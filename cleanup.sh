#!/bin/bash

# Base directory where original files were located
SRC_DIR="src"
TEST_DIR="src/test"
RESOURCES_DIR="src/main/resources"

# Extended list of files/directories to remove
files_to_remove=(
    # Source files
    "$SRC_DIR/main/java/builder"
    "$SRC_DIR/main/java/factory"
    "$SRC_DIR/main/java/model"
    "$SRC_DIR/main/java/notification"
    "$SRC_DIR/main/java/ConsoleInputHandler"
    "$SRC_DIR/main/java/ConsoleInputValidator"
    "$SRC_DIR/main/java/matching"
    "$SRC_DIR/main/java/app"
    "$SRC_DIR/main/java/location"
    "$SRC_DIR/main/java/exception"
    "$SRC_DIR/main/java/orderUtilities"
    
    # Test files
    "$TEST_DIR/java/notification"
    "$TEST_DIR/java/app"
    "$TEST_DIR/java/model"
    "$TEST_DIR/java/builder"
    
    # Build files
    "build.gradle.old"
    "settings.gradle.old"
    ".project"
    ".classpath"
    ".settings"
    
    # IDE files
    ".idea"
    "*.iml"
    ".vscode"
    
    # Build outputs
    "build"
    "bin"
    "out"
    ".gradle"
)

# Directories to preserve
dirs_to_preserve=(
    "OnlineFoodDelieveryRevision"
    "gradle"
    "config"
    "$RESOURCES_DIR/application.properties"
    "$RESOURCES_DIR/logback.xml"
)

# Function to safely remove files/directories
safe_remove() {
    if [ -e "$1" ]; then
        echo "Removing: $1"
        rm -rf "$1"
    else
        echo "Already removed or not found: $1"
    fi
}

# Backup original files
echo "Creating backup of original files..."
timestamp=$(date +%Y%m%d_%H%M%S)
backup_dir="backup_$timestamp"
mkdir -p "$backup_dir"
cp -r "$SRC_DIR" "$backup_dir/" 2>/dev/null || true
cp build.gradle "$backup_dir/" 2>/dev/null || true
cp settings.gradle "$backup_dir/" 2>/dev/null || true

# Remove files
echo "Removing old files..."
for file in "${files_to_remove[@]}"; do
    safe_remove "$file"
done

# Clean up empty directories
echo "Cleaning up empty directories..."
find "$SRC_DIR" -type d -empty -delete 2>/dev/null || true

echo "Cleanup complete!"
echo "Backup created in: $backup_dir"
echo "Please verify the new module structure in OnlineFoodDelieveryRevision/" 