package tn.esprit.spring.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import tn.esprit.spring.batch.InvitationProcessor;
import tn.esprit.spring.batch.InvitationWriter;
import tn.esprit.spring.entities.Invitation;

@Configuration
/*3. Activer le traitement par lot */
@EnableBatchProcessing
@EnableScheduling
public class InvitationBatchConfig {

	/*4. Création des variables de notre batch (nom fichier,
	 * nom job, nom step, nom lecteur*/
	private static final String FILE_NAME ="Invitation.csv";
	private static final String JOB_NAME = "listInvitationsJob";
	private static final String STEP_NAME = "processingStep";
	private static final String READER_NAME = "InvitationItemReader";

	/*5. Lister les champs que nous souhaitons parser dans le
	 * fichier excel*/
	private String names = "IdInvi,Email,State";
/*6. Définir la stratégie de délimitation des différents champs*/
	private String delimiter = ",";

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	
	

	/*7 Créer le bean associé au job et le lancer*/
	@Bean
	public Job listInvitationsJob(Step step1) {
		return jobBuilderFactory.get(JOB_NAME).start(step1).build();
	}

	/*8 Créer le step associé au job et le lancer*/
	@Bean
	public Step InvitationStep() {
		return stepBuilderFactory.get(STEP_NAME).<Invitation, Invitation>chunk(5).reader(InvitationItemReader())
				.processor(InvitationItemProcessor()).writer(InvitationItemWriter()).build();
	}
	
	/*9. étape 1 (ItemReader) Créer le reader pour récupérer les données depuis
	 * le fichier csv*/
	@Bean
	public ItemReader<Invitation> InvitationItemReader() {
		FlatFileItemReader<Invitation> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource(FILE_NAME));
		reader.setName(READER_NAME);
		reader.setLinesToSkip(1);
		/*7. récupérer les données ligne par ligne du fichier excel */
		reader.setLineMapper(invitationLineMapper());
		return reader;

	}

	
	/*10. récupérer les données ligne par ligne du fichier excel */

	@Bean
	public LineMapper<Invitation> invitationLineMapper() {

		final DefaultLineMapper<Invitation> defaultLineMapper = new DefaultLineMapper<>();
		final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(delimiter);
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(names.split(delimiter));
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSet -> {
			Invitation data = new Invitation();
			data.setEmail(fieldSet.readString(0));
			data.setIdInvi(fieldSet.readLong(1));
			data.setState(fieldSet.readBoolean(2));
			return data;
		});
		return defaultLineMapper;
	}

	/* 11. étape 2 (ItemProcessor) fait appel à la classe StockProcessor
	 * qui se charge de modifier la logique des données selon
	 * nos besoins métiers */
	@Bean
	public ItemProcessor<Invitation, Invitation> InvitationItemProcessor() {
		return new InvitationProcessor();
	}

	
	/* 12. étape 3 (ItemWriter) fait appel à la classe StockWriter
	 * qui se charge de lancer le service de sauvegarde des 
	 * données liées à la partie stock dans la BD*/
	@Bean
	public ItemWriter<Invitation> InvitationItemWriter() {
		return new InvitationWriter();
	}
}