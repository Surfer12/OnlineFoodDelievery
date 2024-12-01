#!/bin/bash

# Base directory
BASE_DIR="OnlineFoodDelieveryRevision"

# Create root build.gradle
cat > "$BASE_DIR/build.gradle" << 'EOL'
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.1' apply false
    id 'io.spring.dependency-management' version '1.1.4' apply false
    id 'checkstyle'
}

allprojects {
    group = 'com.yourcompany'
    version = '0.0.1-SNAPSHOT'

    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {
    apply plugin: 'java'
    apply plugin: 'checkstyle'

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    ext {
        junitVersion = '5.10.1'
        mockitoVersion = '5.8.0'
        springBootVersion = '3.2.1'
        slf4jVersion = '2.0.9'
    }

    test {
        useJUnitPlatform()
        testLogging {
            events 'passed', 'skipped', 'failed'
        }
    }

    checkstyle {
        toolVersion = '10.12.4'
        configFile = rootProject.file('config/checkstyle/checkstyle.xml')
        maxWarnings = 0
    }
}

// Configuration cache settings
gradle.startParameter.configurationCache.set(false)
EOL

# Create settings.gradle
cat > "$BASE_DIR/settings.gradle" << 'EOL'
rootProject.name = 'OnlineFoodDelievery'

// Include all necessary modules
include 'app'
include 'core'
include 'notification'
include 'model'
include 'utilities'

// Disable source control for now
// sourceControl is not recommended with configuration cache
EOL

echo "✓ Created build.gradle and settings.gradle in $BASE_DIR"

# Verify the files were created
if [ -f "$BASE_DIR/build.gradle" ] && [ -f "$BASE_DIR/settings.gradle" ]; then
    echo "✓ Build files created successfully!"
    echo "Running verification script..."
    ./verify_migration.sh
else
    echo "✗ Error creating build files!"
fi 