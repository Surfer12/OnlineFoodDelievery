#!/bin/bash

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

# Base directories
NEW_BASE="OnlineFoodDelieveryRevision"
MODULES=("app" "core" "notification" "model" "utilities")

# Define module-specific files using simple variables
APP_FILES="Application.java DeliverySystem.java"
CORE_FILES="OrderBuilder.java MenuItemFactory.java"
MODEL_FILES="Customer.java Driver.java MenuItem.java Order.java"
NOTIFICATION_FILES="NotificationService.java BasicNotificationService.java"
UTILITIES_FILES="ConsoleInputHandler.java InputValidator.java PositiveIntegerValidator.java"

# Check build files
check_build_files() {
    echo "Checking build files..."
    local missing_builds=0
    
    if [ -f "$NEW_BASE/build.gradle" ]; then
        echo -e "${GREEN}✓ Found build.gradle${NC}"
    else
        echo -e "${RED}✗ Missing build.gradle${NC}"
        missing_builds=1
    fi
    
    if [ -f "$NEW_BASE/settings.gradle" ]; then
        echo -e "${GREEN}✓ Found settings.gradle${NC}"
    else
        echo -e "${RED}✗ Missing settings.gradle${NC}"
        missing_builds=1
    fi
    
    return $missing_builds
}

# Check module structure
check_modules() {
    echo "Checking module structure..."
    local missing_modules=0
    
    for module in "${MODULES[@]}"; do
        if [ -d "$NEW_BASE/$module" ]; then
            echo -e "${GREEN}✓ Found module: $module${NC}"
            check_module_files "$module"
        else
            echo -e "${RED}✗ Missing module: $module${NC}"
            missing_modules=1
        fi
    done
    
    return $missing_modules
}

# Check files within each module
check_module_files() {
    local module=$1
    local module_path="$NEW_BASE/$module/src/main/java"
    local files_to_check=""
    
    case $module in
        "app")
            files_to_check="$APP_FILES"
            ;;
        "core")
            files_to_check="$CORE_FILES"
            ;;
        "model")
            files_to_check="$MODEL_FILES"
            ;;
        "notification")
            files_to_check="$NOTIFICATION_FILES"
            ;;
        "utilities")
            files_to_check="$UTILITIES_FILES"
            ;;
    esac
    
    echo "Checking files in $module..."
    for required_file in $files_to_check; do
        if find "$module_path" -name "$required_file" -type f -print -quit | grep -q .; then
            echo -e "${GREEN}  ✓ Found $required_file${NC}"
        else
            echo -e "${RED}  ✗ Missing $required_file${NC}"
        fi
    done
}

# Main verification
main() {
    echo "Starting migration verification..."
    
    local errors=0
    
    # Check if new project directory exists
    if [ ! -d "$NEW_BASE" ]; then
        echo -e "${RED}✗ New project directory not found: $NEW_BASE${NC}"
        exit 1
    fi
    
    # Run checks
    check_build_files
    errors=$((errors + $?))
    
    check_modules
    errors=$((errors + $?))
    
    # Summary
    if [ $errors -eq 0 ]; then
        echo -e "\n${GREEN}✓ Migration verification passed!${NC}"
    else
        echo -e "\n${RED}✗ Migration verification failed with $errors errors${NC}"
    fi
    
    return $errors
}

# Run main verification
main