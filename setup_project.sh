#!/bin/bash

# Base directory
BASE_DIR="OnlineFoodDelieveryRevision"

# Create module directories and package structure
create_module_structure() {
    local module=$1
    mkdir -p "$BASE_DIR/$module/src/main/java"
    mkdir -p "$BASE_DIR/$module/src/test/java"
    mkdir -p "$BASE_DIR/$module/src/main/resources"
    mkdir -p "$BASE_DIR/$module/src/test/resources"
}

# Create all module structures
modules=("app" "core" "notification" "model" "utilities")
for module in "${modules[@]}"; do
    create_module_structure "$module"
done

# Create package directories
mkdir -p "$BASE_DIR/app/src/main/java/app"
mkdir -p "$BASE_DIR/notification/src/main/java/notification"
mkdir -p "$BASE_DIR/model/src/main/java/model"
mkdir -p "$BASE_DIR/core/src/main/java/factory"
mkdir -p "$BASE_DIR/core/src/main/java/orderUtilities"
mkdir -p "$BASE_DIR/utilities/src/main/java/ConsoleInputHandler"
mkdir -p "$BASE_DIR/utilities/src/main/java/ConsoleInputValidator"

# Move files to their new locations
# App module
mv "$BASE_DIR/src/test/java/app/Application.java" "$BASE_DIR/app/src/main/java/app/"

# Notification module
mv "$BASE_DIR/src/main/java/notification/NotificationService.java" "$BASE_DIR/notification/src/main/java/notification/"
mv "$BASE_DIR/src/main/java/notification/BasicNotificationService.java" "$BASE_DIR/notification/src/main/java/notification/"

# Model module (these files need to be created based on your class diagram)
touch "$BASE_DIR/model/src/main/java/model/Customer.java"
touch "$BASE_DIR/model/src/main/java/model/Driver.java"
touch "$BASE_DIR/model/src/main/java/model/MenuItem.java"
touch "$BASE_DIR/model/src/main/java/model/Order.java"
touch "$BASE_DIR/model/src/main/java/model/OrderStatus.java"
touch "$BASE_DIR/model/src/main/java/model/Payment.java"
touch "$BASE_DIR/model/src/main/java/model/Rating.java"

# Core module
touch "$BASE_DIR/core/src/main/java/factory/MenuItemFactory.java"
touch "$BASE_DIR/core/src/main/java/orderUtilities/OrderBuilder.java"

# Utilities module
touch "$BASE_DIR/utilities/src/main/java/ConsoleInputHandler/ConsoleInputHandler.java"
touch "$BASE_DIR/utilities/src/main/java/ConsoleInputValidator/InputValidator.java"
touch "$BASE_DIR/utilities/src/main/java/ConsoleInputValidator/PositiveIntegerValidator.java"

# Copy build.gradle files
cat > "$BASE_DIR/app/build.gradle" << 'EOL'
plugins {
    id 'java'
    id 'application'
    id 'org.springframework.boot'
}

dependencies {
    implementation project(':core')
    implementation project(':notification')
    implementation project(':model')
    implementation project(':utilities')
    
    implementation 'org.springframework.boot:spring-boot-starter'
    implementation "org.slf4j:slf4j-api:${slf4jVersion}"
    implementation 'ch.qos.logback:logback-classic:1.4.14'
    
    testImplementation "org.junit.jupiter:junit-jupiter-api:${junitVersion}"
    testImplementation "org.mockito:mockito-core:${mockitoVersion}"
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

application {
    mainClass = 'app.Application'
}
EOL

cat > "$BASE_DIR/core/build.gradle" << 'EOL'
plugins {
    id 'java-library'
}

dependencies {
    api project(':model')
    implementation project(':utilities')
    implementation project(':notification')
    
    implementation 'javax.validation:validation-api:2.0.1.Final'
    implementation 'org.hibernate.validator:hibernate-validator:8.0.1.Final'
    implementation 'com.google.guava:guava:32.1.3-jre'
    
    testImplementation "org.junit.jupiter:junit-jupiter-api:${junitVersion}"
    testImplementation "org.mockito:mockito-core:${mockitoVersion}"
}
EOL

cat > "$BASE_DIR/notification/build.gradle" << 'EOL'
plugins {
    id 'java-library'
}

dependencies {
    implementation "org.slf4j:slf4j-api:${slf4jVersion}"
    implementation 'ch.qos.logback:logback-classic:1.4.14'
    
    testImplementation "org.junit.jupiter:junit-jupiter-api:${junitVersion}"
    testImplementation "org.mockito:mockito-core:${mockitoVersion}"
}
EOL

cat > "$BASE_DIR/model/build.gradle" << 'EOL'
plugins {
    id 'java-library'
}

dependencies {
    implementation 'javax.validation:validation-api:2.0.1.Final'
    implementation 'org.hibernate.validator:hibernate-validator:8.0.1.Final'
    implementation 'org.apache.commons:commons-lang3:3.12.0'
    
    testImplementation "org.junit.jupiter:junit-jupiter-api:${junitVersion}"
    testImplementation "org.mockito:mockito-core:${mockitoVersion}"
}
EOL

cat > "$BASE_DIR/utilities/build.gradle" << 'EOL'
plugins {
    id 'java-library'
}

dependencies {
    implementation 'com.google.guava:guava:32.1.3-jre'
    implementation 'org.apache.commons:commons-lang3:3.12.0'
    implementation "org.slf4j:slf4j-api:${slf4jVersion}"
    
    testImplementation "org.junit.jupiter:junit-jupiter-api:${junitVersion}"
    testImplementation "org.mockito:mockito-core:${mockitoVersion}"
}
EOL

# Make the script executable
chmod +x setup_project.sh

echo "Project structure has been created successfully!" 