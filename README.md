# CodeAlpha_JavaGradleBuild — Task 3: Java Application using Gradle

Automated build, dependency management, testing, coverage and CI/CD for a
**Student Management System** written in Java and built entirely with **Gradle**.

---

## 🎯 Internship objectives (PDF) → where they are implemented

| PDF objective | Implementation |
|---|---|
| Automate Java project builds using Gradle | `build.gradle` + Gradle Wrapper (`./gradlew`) — one command builds everything |
| Manage dependencies efficiently | Gradle resolves `gson` + `junit-jupiter` from Maven Central (`dependencies {}` block) |
| Integrate CI/CD pipelines for continuous delivery | `.github/workflows/gradle-ci.yml` (GitHub Actions): build → test → coverage → JAR artifact on every push |
| Streamline build and deployment processes | Executable **fat JAR** (`java -jar build/libs/student-management-system-1.0.0.jar`) produced by the `jar` task |
| Understand core DevOps principles | Reproducible builds, automated tests as quality gate, versioned artifacts, pipeline-as-code |

---

## 🧰 Prerequisites

- JDK 11 or newer (JDK 17 recommended) — https://adoptium.net/
- Git — https://git-scm.com/
- **No manual Gradle install needed** — the Gradle Wrapper downloads the correct version automatically.

## ▶️ Quick start

```bash
# Windows (PowerShell / cmd)
.\gradlew.bat clean build

# macOS / Linux
./gradlew clean build

# Run the interactive console app
./gradlew run                # (Windows: .\gradlew.bat run)

# Run the CI-friendly demo mode
./gradlew run --args="--demo"

# Run the packaged executable JAR
java -jar build/libs/student-management-system-1.0.0.jar --demo
```

## 🧩 Useful Gradle tasks

| Command | What it does |
|---|---|
| `./gradlew clean` | Deletes `build/` output |
| `./gradlew build` | Compiles, runs tests, packages the fat JAR |
| `./gradlew test` | Runs the JUnit 5 test suite |
| `./gradlew jacocoTestReport` | Generates code-coverage report (HTML + XML) |
| `./gradlew run` | Launches the interactive app |
| `./gradlew run --args="--demo"` | Non-interactive demo run |
| `./gradlew dependencies --configuration runtimeClasspath` | Shows the resolved dependency tree |

## 📁 Project structure

```
CodeAlpha_JavaGradleBuild/
├── build.gradle                  # build automation, deps, test, coverage, fat-jar
├── settings.gradle               # project name
├── gradlew / gradlew.bat         # Gradle Wrapper (reproducible builds)
├── gradle/wrapper/               # wrapper jar + properties
├── .github/workflows/gradle-ci.yml   # CI/CD pipeline (GitHub Actions)
└── src/
    ├── main/java/com/codealpha/sms/
    │   ├── Main.java             # interactive menu + --demo mode
    │   ├── Student.java          # domain model (average + grade logic)
    │   └── StudentManager.java   # CRUD + Gson JSON export
    └── test/java/com/codealpha/sms/
        └── StudentManagerTest.java   # 6 JUnit 5 unit tests
```

## 🧪 Tests & coverage

6 unit tests cover: add/list ordering, duplicate-ID rejection, search, delete,
average+grade calculation, and JSON export.

- Test report: `build/reports/tests/test/index.html`
- Coverage report: `build/reports/jacoco/test/html/index.html`

## 🔄 CI/CD pipeline (GitHub Actions)

On every push / pull request to `main`:

1. Checkout code
2. Install Temurin JDK 17
3. `./gradlew clean build jacocoTestReport`  ← **quality gate: failing tests break the build**
4. Upload executable JAR as a deployment artifact
5. Upload test + coverage reports as artifacts

Green tick = shippable. This is the "continuous integration / continuous delivery" loop.

## 📸 Screenshots to capture for submission

- [ ] `./gradlew clean build` → BUILD SUCCESSFUL terminal
- [ ] Test report HTML (6/6 passed)
- [ ] JaCoCo coverage report
- [ ] GitHub Actions workflow green run
- [ ] Downloaded JAR artifact running (`java -jar ... --demo`)
- [ ] Interactive menu in use
