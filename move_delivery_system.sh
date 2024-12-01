#!/bin/bash

# Source and destination paths
SRC_FILE="src/test/java/app/DeliverySystem.java"
DEST_DIR="OnlineFoodDelieveryRevision/app/src/main/java/app"

# Create destination directory if it doesn't exist
mkdir -p "$DEST_DIR"

# Move the file
if [ -f "$SRC_FILE" ]; then
    cp "$SRC_FILE" "$DEST_DIR/"
    echo "✓ Moved DeliverySystem.java to $DEST_DIR"
else
    echo "✗ Source file not found: $SRC_FILE"
    
    # Create DeliverySystem.java if it doesn't exist
    cat > "$DEST_DIR/DeliverySystem.java" << 'EOL'
package app;

import model.Order;
import model.Driver;

public class DeliverySystem {
    public void submitOrder(Order order) {
        // Placeholder implementation
        System.out.println("Order submitted: " + order);
    }

    public void assignOrderToDriver(Order order, Driver driver) {
        // Placeholder implementation
        System.out.println("Order assigned to driver: " + driver);
    }

    public void completeDelivery(Long orderId, Long driverId) {
        // Placeholder implementation
        System.out.println("Delivery completed for order: " + orderId);
    }
}
EOL
    echo "✓ Created new DeliverySystem.java in $DEST_DIR"
fi 