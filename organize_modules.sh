#!/bin/bash

# Base directory
BASE_DIR="OnlineFoodDelieveryRevision"
SRC_DIR="src/main/java"
TEST_DIR="src/test/java"

# Function to create directory if it doesn't exist
create_dir() {
    if [ ! -d "$1" ]; then
        mkdir -p "$1"
    fi
}

# Function to move file if it exists
move_file() {
    local src=$1
    local dest=$2
    if [ -f "$src" ]; then
        mkdir -p "$(dirname "$dest")"
        cp "$src" "$dest"
        echo "Moved: $src -> $dest"
    else
        echo "Warning: Source file not found: $src"
    fi
}

# Create module directories
modules=("app" "core" "notification" "model" "utilities")
for module in "${modules[@]}"; do
    create_dir "$BASE_DIR/$module/$SRC_DIR"
    create_dir "$BASE_DIR/$module/$TEST_DIR"
done

# Move app module files
move_file "src/test/java/app/Application.java" "$BASE_DIR/app/$SRC_DIR/app/Application.java"
move_file "src/main/java/app/DeliverySystem.java" "$BASE_DIR/app/$SRC_DIR/app/DeliverySystem.java"

# Move core module files
move_file "src/main/java/builder/OrderBuilder.java" "$BASE_DIR/core/$SRC_DIR/builder/OrderBuilder.java"
move_file "src/main/java/factory/MenuItemFactory.java" "$BASE_DIR/core/$SRC_DIR/factory/MenuItemFactory.java"
move_file "src/main/java/matching/DriverMatchingStrategy.java" "$BASE_DIR/core/$SRC_DIR/matching/DriverMatchingStrategy.java"

# Move model module files
model_files=(
    "Customer"
    "Driver"
    "MenuItem"
    "Order"
    "OrderStatus"
    "Payment"
    "Rating"
    "Location"
)

for file in "${model_files[@]}"; do
    move_file "src/main/java/model/$file.java" "$BASE_DIR/model/$SRC_DIR/model/$file.java"
done

# Move notification module files
move_file "src/main/java/notification/NotificationService.java" "$BASE_DIR/notification/$SRC_DIR/notification/NotificationService.java"
move_file "src/main/java/notification/BasicNotificationService.java" "$BASE_DIR/notification/$SRC_DIR/notification/BasicNotificationService.java"

# Move utilities module files
move_file "src/main/java/ConsoleInputHandler/ConsoleInputHandler.java" "$BASE_DIR/utilities/$SRC_DIR/ConsoleInputHandler/ConsoleInputHandler.java"
move_file "src/main/java/ConsoleInputValidator/InputValidator.java" "$BASE_DIR/utilities/$SRC_DIR/ConsoleInputValidator/InputValidator.java"
move_file "src/main/java/ConsoleInputValidator/PositiveIntegerValidator.java" "$BASE_DIR/utilities/$SRC_DIR/ConsoleInputValidator/PositiveIntegerValidator.java"

# Move test files
move_file "src/test/java/notification/NotificationServiceTest.java" "$BASE_DIR/notification/$TEST_DIR/notification/NotificationServiceTest.java"

# Create module-info.java files for each module
cat > "$BASE_DIR/app/$SRC_DIR/module-info.java" << 'EOL'
module app {
    requires core;
    requires model;
    requires notification;
    requires utilities;
    requires org.slf4j;
}
EOL

cat > "$BASE_DIR/core/$SRC_DIR/module-info.java" << 'EOL'
module core {
    requires model;
    requires notification;
    requires utilities;
    exports builder;
    exports factory;
    exports matching;
}
EOL

cat > "$BASE_DIR/model/$SRC_DIR/module-info.java" << 'EOL'
module model {
    exports model;
}
EOL

cat > "$BASE_DIR/notification/$SRC_DIR/module-info.java" << 'EOL'
module notification {
    requires model;
    exports notification;
}
EOL

cat > "$BASE_DIR/utilities/$SRC_DIR/module-info.java" << 'EOL'
module utilities {
    exports ConsoleInputHandler;
    exports ConsoleInputValidator;
}
EOL

# Create .gitignore
cat > "$BASE_DIR/.gitignore" << 'EOL'
# Gradle
.gradle/
build/
!gradle/wrapper/gradle-wrapper.jar

# IDE
.idea/
*.iml
*.iws
*.ipr
.settings/
.project
.classpath
.vscode/

# Compiled class files
*.class

# Log files
*.log

# Package files
*.jar
*.war
*.nar
*.ear
*.zip
*.tar.gz
*.rar

# virtual machine crash logs
hs_err_pid*
EOL

echo "Module organization complete!"
echo "Please review the moved files and update any necessary import statements."
echo "Next steps:"
echo "1. Review and update package declarations in each file"
echo "2. Update import statements to match new package structure"
echo "3. Run './gradlew build' to verify the setup" 