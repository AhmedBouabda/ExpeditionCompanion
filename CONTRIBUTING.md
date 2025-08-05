# Contributing to Expedition Companion

We love your input! We want to make contributing to Expedition Companion as easy and transparent as possible, whether it's:

- Reporting a bug
- Discussing the current state of the code
- Submitting a fix
- Proposing new features
- Becoming a maintainer

## Development Process

We use GitHub to host code, to track issues and feature requests, as well as accept pull requests.

## Code of Conduct

By participating in this project, you are expected to uphold our Code of Conduct:

- Use welcoming and inclusive language
- Be respectful of differing viewpoints and experiences
- Gracefully accept constructive criticism
- Focus on what is best for the community
- Show empathy towards other community members

## Getting Started

### Prerequisites

Before contributing, make sure you have:

- Java 8 or higher installed
- Maven 3.6+ installed
- MySQL database running
- Git configured with your GitHub account
- Basic understanding of Spring Boot

### Setting Up Development Environment

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/your-username/ExpeditionCompanion.git
   cd ExpeditionCompanion
   ```
3. **Add the original repository** as upstream:
   ```bash
   git remote add upstream https://github.com/AhmedBouabda/ExpeditionCompanion.git
   ```
4. **Set up database** (see README.md for details)
5. **Configure application properties** with your database settings
6. **Build and run tests**:
   ```bash
   ./mvnw clean compile
   ./mvnw test
   ```

## How to Contribute

### Reporting Bugs

Bug reports help make Expedition Companion better. When filing a bug report, please include:

1. **Use a clear and descriptive title**
2. **Describe the exact steps to reproduce the problem**
3. **Provide specific examples** to demonstrate the steps
4. **Describe the behavior you observed** and **what behavior you expected**
5. **Include screenshots** if applicable
6. **Include your environment details**:
   - OS and version
   - Java version
   - Maven version
   - MySQL version

**Use the Bug Report template:**

```markdown
## Bug Description
A clear and concise description of what the bug is.

## Steps to Reproduce
1. Go to '...'
2. Click on '....'
3. Scroll down to '....'
4. See error

## Expected Behavior
A clear and concise description of what you expected to happen.

## Actual Behavior
A clear and concise description of what actually happened.

## Screenshots
If applicable, add screenshots to help explain your problem.

## Environment
- OS: [e.g. Windows 10, macOS 12.0, Ubuntu 20.04]
- Java version: [e.g. 8, 11, 17]
- Maven version: [e.g. 3.8.1]
- MySQL version: [e.g. 8.0.28]

## Additional Context
Add any other context about the problem here.
```

### Suggesting Features

Feature requests are welcome! Please provide:

1. **Use a clear and descriptive title**
2. **Provide a detailed description** of the suggested feature
3. **Explain why this feature would be useful** to most users
4. **Include mockups or examples** if applicable

### Making Changes

#### For Minor Changes
- Documentation improvements
- Small bug fixes
- Code style improvements

Create a pull request directly.

#### For Major Changes
- New features
- Breaking changes
- Large refactoring

Please create an issue first to discuss the change.

### Pull Request Process

1. **Create a feature branch** from `main`:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. **Make your changes** following our coding standards

3. **Add or update tests** for your changes

4. **Update documentation** as needed

5. **Ensure tests pass**:
   ```bash
   ./mvnw test
   ```

6. **Commit your changes** with a clear commit message:
   ```bash
   git commit -m "Add feature: description of what you added"
   ```

7. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```

8. **Create a Pull Request** on GitHub

### Pull Request Guidelines

- **Fill out the PR template** completely
- **Link to related issues** using keywords (fixes #123, closes #456)
- **Include screenshots** for UI changes
- **Ensure CI passes** before requesting review
- **Keep PRs focused** - one feature/fix per PR
- **Write meaningful commit messages**

**PR Template:**

```markdown
## Description
Brief description of changes made.

## Type of Change
- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] Documentation update

## Testing
- [ ] Tests pass locally
- [ ] New tests added for new functionality
- [ ] Manual testing completed

## Screenshots (if applicable)
Include screenshots of UI changes.

## Checklist
- [ ] My code follows the style guidelines
- [ ] I have performed a self-review
- [ ] I have commented my code where necessary
- [ ] I have updated the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix is effective or that my feature works
- [ ] New and existing unit tests pass locally
```

## Coding Standards

### Java Code Style

- Follow **Google Java Style Guide**
- Use **meaningful variable and method names**
- Add **Javadoc comments** for public methods
- Keep methods **small and focused**
- Use **Spring Boot conventions**

### Example:

```java
/**
 * Creates a new expedition with the provided details.
 *
 * @param expeditionRequest the expedition details
 * @return the created expedition with generated ID
 * @throws IllegalArgumentException if expedition request is invalid
 */
@PostMapping("/expeditions")
public ResponseEntity<Expedition> createExpedition(@Valid @RequestBody ExpeditionRequest expeditionRequest) {
    Expedition expedition = expeditionService.createExpedition(expeditionRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(expedition);
}
```

### Commit Message Format

Use the following format for commit messages:

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation only changes
- `style`: Code style changes (formatting, missing semi-colons, etc)
- `refactor`: Code refactoring
- `test`: Adding missing tests
- `chore`: Changes to build process or auxiliary tools

**Examples:**
```bash
feat(expedition): add expedition creation endpoint
fix(database): resolve connection timeout issue
docs(readme): update installation instructions
```

### Testing Guidelines

- **Write tests** for all new features
- **Maintain test coverage** above 80%
- Use **meaningful test names** that describe what is being tested
- Follow **AAA pattern** (Arrange, Act, Assert)

```java
@Test
void shouldCreateExpeditionWhenValidDataProvided() {
    // Arrange
    ExpeditionRequest request = new ExpeditionRequest("Mountain Expedition", "2024-06-01");
    
    // Act
    Expedition result = expeditionService.createExpedition(request);
    
    // Assert
    assertThat(result.getId()).isNotNull();
    assertThat(result.getName()).isEqualTo("Mountain Expedition");
    assertThat(result.getStartDate()).isEqualTo("2024-06-01");
}
```

## Documentation Standards

- Keep **README.md updated** with new features
- Add **API documentation** for new endpoints
- Include **code comments** for complex logic
- Update **configuration examples** when needed

## Review Process

1. **Automated checks** must pass (CI/CD pipeline)
2. **Code review** by at least one maintainer
3. **Manual testing** if applicable
4. **Documentation review** for user-facing changes

## Community

### Communication Channels

- **GitHub Issues**: Bug reports and feature requests
- **GitHub Discussions**: General questions and discussions
- **Pull Request Comments**: Code review discussions

### Getting Help

If you need help:

1. **Check existing issues** for similar problems
2. **Read the documentation** (README, wiki)
3. **Ask in GitHub Discussions**
4. **Create a new issue** with the "question" label

## Recognition

Contributors will be recognized in:

- **CONTRIBUTORS.md** file
- **Release notes** for significant contributions
- **Special mentions** in project updates

## License

By contributing, you agree that your contributions will be licensed under the same license as the project (MIT License).

## Questions?

Feel free to contact the maintainers:

- **Ahmed Bouabda**: [@AhmedBouabda](https://github.com/AhmedBouabda)

Thank you for contributing to Expedition Companion! 🚀