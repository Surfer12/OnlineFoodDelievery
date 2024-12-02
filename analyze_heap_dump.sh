#!/bin/bash

# Heap Dump Analysis Script

# Check if a heap dump file is provided
if [ $# -eq 0 ]; then
    echo "Usage: $0 <heap_dump_file.hprof>"
    exit 1
fi

HEAP_DUMP_FILE=$1

# Ensure jhat is available
if ! command -v jhat &> /dev/null; then
    echo "jhat could not be found. Please install JDK tools."
    exit 1
fi

# Create output directory
OUTPUT_DIR="heap_analysis_$(date +%Y%m%d_%H%M%S)"
mkdir -p "$OUTPUT_DIR"

# Run jhat analysis
echo "Analyzing heap dump: $HEAP_DUMP_FILE"
jhat -J-Xmx4g "$HEAP_DUMP_FILE" -port 7000 > "$OUTPUT_DIR/jhat_analysis.txt" 2>&1 &

# Wait a moment for jhat to start
sleep 5

echo "Heap dump analysis started:"
echo "1. Analysis log: $OUTPUT_DIR/jhat_analysis.txt"
echo "2. Open http://localhost:7000 in your browser for interactive analysis"
echo "3. Press Ctrl+C to stop the analysis server"

# Keep the script running
wait 