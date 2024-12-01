#!/bin/bash

# Function to preserve directories
preserve_directory() {
    local dir=$1
    local backup_dir=$2
    
    if [ -d "$dir" ]; then
        echo "Preserving $dir..."
        cp -r "$dir" "$backup_dir/"
    fi
}

# Create preservation directory
timestamp=$(date +%Y%m%d_%H%M%S)
preserve_dir="preserved_$timestamp"
mkdir -p "$preserve_dir"

# List of directories to preserve
dirs_to_preserve=(
    "OnlineFoodDelieveryRevision"
    "gradle"
    "config"
    "src/main/resources/application.properties"
    "src/main/resources/logback.xml"
    ".git"
    "docs"
    "OnlineFoodDelieveryRevision/workflows"
    "OnlineFoodDelieveryRevision/gradle"
    "OnlineFoodDelieveryRevision/.github"
    "config/checkstyle"
)

# Preserve each directory
for dir in "${dirs_to_preserve[@]}"; do
    preserve_directory "$dir" "$preserve_dir"
done

echo "Directories preserved in: $preserve_dir" 