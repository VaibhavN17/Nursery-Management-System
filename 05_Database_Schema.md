# DATABASE SCHEMA DOCUMENT
## Nursery Management System
**Version:** 1.0  
**Date:** May 2025  
**Database:** PostgreSQL 15+

---

## 1. ER DIAGRAM (TEXT FORMAT)

### 1.1 High-Level Relationships

```
┌─────────────────────────────────────────────────────────────────┐
│                        CORE ENTITIES                             │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│  Users ◄──────────► Roles ◄──────────► Permissions              │
│   │                                                              │
│   ├──► Nurseries (Multi-tenant)                                │
│   │      │                                                      │
│   │      ├──► Locations                                        │
│   │      │      │                                              │
│   │      │      ├──► Inventory                                │
│   │      │      │      └──► Inventory_Movements               │
│   │      │      │                                              │
│   │      │      ├──► Greenhouse                               │
│   │      │      │      ├──► Sensors                           │
│   │      │      │      │      └──► Sensor_Readings           │
│   │      │      │      └──► Greenhouse_Alerts                │
│   │      │      │                                              │
│   │      │      └──► Deliveries                              │
│   │      │             └──► Delivery_Items                    │
│   │      │                                                    │
│   │      ├──► Products                                       │
│   │      │      ├──► Categories                              │
│   │      │      ├──► Inventory (via Inventory table)          │
│   │      │      └──► Order_Items                             │
│   │      │                                                    │
│   │      ├──► Customers                                      │
│   │      │      ├──► Orders                                  │
│   │      │      │      ├──► Order_Items                      │
│   │      │      │      ├──► Payments                         │
│   │      │      │      ├──► Invoices                         │
│   │      │      │      └──► Deliveries                       │
│   │      │      │                                             │
│   │      │      └──► Customer_Preferences                    │
│   │      │                                                    │
│   │      ├──► Suppliers                                      │
│   │      │      ├──► Products (supplied_by)                 │
│   │      │      └──► Purchase_Orders                        │
│   │      │             └──► PO_Items                         │
│   │      │                                                    │
│   │      └──► Notifications                                  │
│   │             └──► Notification_Preferences                │
│   │                                                          │
│   └──► Audit_Log (activity tracking)                        │
│                                                              │
└──────────────────────────────────────────────────────────────┘
```

### 1.2 Detailed Relationships

```
USERS (1) ───────────────────┬─────────────────────── (N) ROLES
                              │
                              ├─ user_id (FK)
                              │
NURSERIES (1) ─────────────── (N) LOCATIONS
                              │
                              ├─ nursery_id (FK)
                              │
LOCATIONS (1) ─────────────── (N) INVENTORY
PRODUCTS (1) ┐              │
             ├─────────────── (N) INVENTORY
NURSERIES (1)┘               │
                              ├─ product_id (FK)
                              ├─ location_id (FK)
                              │
PRODUCTS (1) ─────────────── (N) PRODUCT_VARIANTS
CATEGORIES (1) ───────────── (N) PRODUCTS
SUPPLIERS (1) ─────────────── (N) PRODUCTS
                              │
CUSTOMERS (1) ───────────────┬─ (N) ORDERS
NURSERIES (1) ┘              │
                              ├─ customer_id (FK)
                              │
ORDERS (1) ────────────────── (N) ORDER_ITEMS
PRODUCTS (1) ┘

ORDER_ITEMS (1) ──────────── (N) INVENTORY_MOVEMENTS
ORDERS (1) ────────────────── (N) PAYMENTS
ORDERS (1) ────────────────── (N) INVOICES
ORDERS (1) ────────────────── (N) DELIVERIES

DELIVERY_AGENTS (1) ────────── (N) DELIVERIES
LOCATIONS (1) ─────────────┬── (N) GREENHOUSE
NURSERIES (1) ──────────── (N) GREENHOUSE_SENSORS (via Greenhouse)
SENSORS (1) ───────────────── (N) SENSOR_READINGS
GREENHOUSE (1) ──────────── (N) GREENHOUSE_ALERTS
```

---

## 2. TABLE DEFINITIONS

### 2.1 User & Authentication Tables

#### users
```sql
CREATE TABLE users (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100),
  phone VARCHAR(20),
  
  -- Foreign Keys
  nursery_id UUID NOT NULL REFERENCES nurseries(id),
  role_id UUID NOT NULL REFERENCES roles(id),
  
  -- MFA
  mfa_enabled BOOLEAN DEFAULT false,
  mfa_secret VARCHAR(255),
  backup_codes TEXT[], -- Array of encrypted codes
  
  -- Status
  is_active BOOLEAN DEFAULT true,
  last_login_at TIMESTAMP,
  password_changed_at TIMESTAMP,
  
  -- Audit
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP, -- Soft delete
  created_by UUID REFERENCES users(id),
  
  CONSTRAINT user_email_unique UNIQUE(email, nursery_id)
);

CREATE INDEX idx_users_email ON users(email) WHERE deleted_at IS NULL;
CREATE INDEX idx_users_nursery ON users(nursery_id) WHERE is_active = true;
CREATE INDEX idx_users_role ON users(role_id);
```

#### roles
```sql
CREATE TABLE roles (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  code VARCHAR(50) NOT NULL UNIQUE, -- SUPER_ADMIN, OWNER, MANAGER, etc.
  name VARCHAR(100) NOT NULL,
  description TEXT,
  is_active BOOLEAN DEFAULT true,
  
  -- Audit
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  
  CONSTRAINT role_code_unique UNIQUE(code)
);
```

#### permissions
```sql
CREATE TABLE permissions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  code VARCHAR(100) NOT NULL UNIQUE, -- manage_inventory, create_order, etc.
  name VARCHAR(100) NOT NULL,
  description TEXT,
  module VARCHAR(50), -- inventory, orders, customers, etc.
  is_active BOOLEAN DEFAULT true,
  
  -- Audit
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP
);
```

#### role_permissions
```sql
CREATE TABLE role_permissions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
  permission_id UUID NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT role_permission_unique UNIQUE(role_id, permission_id)
);

CREATE INDEX idx_role_permissions_role ON role_permissions(role_id);
```

#### sessions
```sql
CREATE TABLE sessions (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  token_hash VARCHAR(255) NOT NULL UNIQUE,
  refresh_token_hash VARCHAR(255) NOT NULL UNIQUE,
  
  ip_address VARCHAR(45),
  user_agent TEXT,
  device_info JSONB, -- Mobile, Browser, etc.
  
  expires_at TIMESTAMP NOT NULL,
  last_activity_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  is_active BOOLEAN DEFAULT true,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  CREATE INDEX idx_sessions_user ON sessions(user_id);
  CREATE INDEX idx_sessions_expires ON sessions(expires_at);
);
```

---

### 2.2 Nursery & Location Tables

#### nurseries
```sql
CREATE TABLE nurseries (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  -- Basic Info
  name VARCHAR(255) NOT NULL,
  description TEXT,
  registration_number VARCHAR(100) UNIQUE,
  gst_number VARCHAR(50),
  
  -- Contact
  primary_email VARCHAR(255) NOT NULL,
  primary_phone VARCHAR(20) NOT NULL,
  
  -- Address
  address TEXT NOT NULL,
  city VARCHAR(100),
  state VARCHAR(100),
  zip_code VARCHAR(20),
  country VARCHAR(100),
  latitude DECIMAL(10, 8),
  longitude DECIMAL(11, 8),
  
  -- Subscription
  subscription_tier VARCHAR(50) DEFAULT 'professional', -- starter, professional, enterprise
  subscription_status VARCHAR(50) DEFAULT 'active', -- active, inactive, suspended
  max_users INT DEFAULT 10,
  max_locations INT DEFAULT 3,
  max_products INT DEFAULT 1000,
  features JSONB, -- {"has_greenhouse": true, "has_ecommerce": false}
  
  -- Branding
  logo_url TEXT,
  website_url TEXT,
  
  -- Status
  is_active BOOLEAN DEFAULT true,
  
  -- Audit
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP
);

CREATE INDEX idx_nurseries_gst ON nurseries(gst_number);
CREATE INDEX idx_nurseries_active ON nurseries(is_active) WHERE deleted_at IS NULL;
```

#### locations
```sql
CREATE TABLE locations (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  nursery_id UUID NOT NULL REFERENCES nurseries(id) ON DELETE CASCADE,
  
  name VARCHAR(255) NOT NULL,
  location_type VARCHAR(50), -- warehouse, retail, greenhouse, etc.
  
  address TEXT NOT NULL,
  city VARCHAR(100),
  state VARCHAR(100),
  zip_code VARCHAR(20),
  latitude DECIMAL(10, 8),
  longitude DECIMAL(11, 8),
  
  manager_id UUID REFERENCES users(id),
  phone VARCHAR(20),
  email VARCHAR(255),
  
  max_capacity INT, -- Maximum inventory units
  current_stock INT GENERATED ALWAYS AS (
    SELECT COALESCE(SUM(quantity_on_hand), 0)
    FROM inventory WHERE location_id = locations.id
  ) STORED,
  
  is_active BOOLEAN DEFAULT true,
  
  -- Audit
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  
  CONSTRAINT location_name_unique UNIQUE(nursery_id, name)
);

CREATE INDEX idx_locations_nursery ON locations(nursery_id);
CREATE INDEX idx_locations_manager ON locations(manager_id);
```

---

### 2.3 Product & Inventory Tables

#### categories
```sql
CREATE TABLE categories (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  nursery_id UUID NOT NULL REFERENCES nurseries(id) ON DELETE CASCADE,
  
  name VARCHAR(255) NOT NULL,
  description TEXT,
  parent_category_id UUID REFERENCES categories(id),
  
  icon_url TEXT,
  sort_order INT DEFAULT 0,
  is_active BOOLEAN DEFAULT true,
  
  -- Audit
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  
  CONSTRAINT category_name_unique UNIQUE(nursery_id, name)
);

CREATE INDEX idx_categories_nursery ON categories(nursery_id);
CREATE INDEX idx_categories_parent ON categories(parent_category_id);
```

#### products
```sql
CREATE TABLE products (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  nursery_id UUID NOT NULL REFERENCES nurseries(id) ON DELETE CASCADE,
  
  sku VARCHAR(100) NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  detailed_care_instructions TEXT,
  
  -- Classification
  category_id UUID REFERENCES categories(id),
  supplier_id UUID REFERENCES suppliers(id),
  plant_family VARCHAR(100),
  plant_scientific_name VARCHAR(255),
  
  -- Pricing
  unit_price DECIMAL(10, 2) NOT NULL,
  cost_price DECIMAL(10, 2),
  wholesale_price DECIMAL(10, 2),
  
  -- Units
  quantity_unit VARCHAR(50), -- piece, kg, liter, dozen
  
  -- Inventory Control
  min_stock_level INT DEFAULT 10,
  max_stock_level INT DEFAULT 1000,
  reorder_quantity INT DEFAULT 50,
  lead_time_days INT DEFAULT 7,
  
  -- Specifications
  dimensions JSONB, -- {"height": "30cm", "width": "20cm", "pot_size": "8inch"}
  weight DECIMAL(10, 2),
  shelf_life_days INT,
  
  -- Images & Media
  image_urls TEXT[], -- Array of image URLs
  
  -- Status
  is_active BOOLEAN DEFAULT true,
  
  -- Timestamps
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  
  CONSTRAINT product_sku_unique UNIQUE(nursery_id, sku)
);

CREATE INDEX idx_products_sku ON products(sku) WHERE is_active = true;
CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_supplier ON products(supplier_id);
```

#### inventory
```sql
CREATE TABLE inventory (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  product_id UUID NOT NULL REFERENCES products(id) ON DELETE CASCADE,
  location_id UUID NOT NULL REFERENCES locations(id) ON DELETE CASCADE,
  
  quantity_on_hand INT NOT NULL DEFAULT 0,
  quantity_reserved INT NOT NULL DEFAULT 0,
  quantity_available INT GENERATED ALWAYS AS (
    quantity_on_hand - quantity_reserved
  ) STORED,
  
  -- Batch Tracking
  batch_number VARCHAR(100),
  lot_number VARCHAR(100),
  manufacture_date DATE,
  expiry_date DATE,
  
  -- Bin Location
  bin_location VARCHAR(50), -- Aisle-Rack-Shelf-Position
  
  -- Last Operations
  last_counted_at TIMESTAMP,
  count_variance INT, -- Discrepancy from last count
  
  -- Status
  status VARCHAR(50) DEFAULT 'active', -- active, blocked, obsolete
  
  -- Timestamps
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  -- Constraints
  CONSTRAINT inventory_unique UNIQUE(product_id, location_id, batch_number),
  CONSTRAINT quantity_non_negative CHECK (quantity_on_hand >= 0),
  CONSTRAINT reserved_non_negative CHECK (quantity_reserved >= 0)
);

CREATE INDEX idx_inventory_product ON inventory(product_id);
CREATE INDEX idx_inventory_location ON inventory(location_id);
CREATE INDEX idx_inventory_batch ON inventory(batch_number) WHERE batch_number IS NOT NULL;
CREATE INDEX idx_inventory_expiry ON inventory(expiry_date) WHERE expiry_date IS NOT NULL;
```

#### inventory_movements
```sql
CREATE TABLE inventory_movements (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  product_id UUID NOT NULL REFERENCES products(id),
  location_id UUID NOT NULL REFERENCES locations(id),
  
  movement_type VARCHAR(50) NOT NULL, -- in, out, adjustment, transfer, damage, waste
  quantity INT NOT NULL,
  
  source_location_id UUID REFERENCES locations(id), -- For transfers
  reference_type VARCHAR(50), -- order, purchase_order, adjustment, etc.
  reference_id UUID,
  
  reason TEXT,
  notes TEXT,
  
  batch_number VARCHAR(100),
  performed_by UUID NOT NULL REFERENCES users(id),
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT movement_quantity_valid CHECK (quantity > 0)
);

CREATE INDEX idx_movements_product ON inventory_movements(product_id);
CREATE INDEX idx_movements_location ON inventory_movements(location_id);
CREATE INDEX idx_movements_type ON inventory_movements(movement_type);
CREATE INDEX idx_movements_date ON inventory_movements(created_at DESC);
```

---

### 2.4 Customer & Order Tables

#### customers
```sql
CREATE TABLE customers (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  nursery_id UUID NOT NULL REFERENCES nurseries(id) ON DELETE CASCADE,
  
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100),
  
  -- Address
  address TEXT,
  city VARCHAR(100),
  state VARCHAR(100),
  zip_code VARCHAR(20),
  country VARCHAR(100),
  
  -- Business Info
  customer_type VARCHAR(50) DEFAULT 'retail', -- retail, wholesale, corporate
  gst_number VARCHAR(50),
  company_name VARCHAR(255),
  
  -- Loyalty
  loyalty_points INT DEFAULT 0,
  total_spent DECIMAL(14, 2) DEFAULT 0,
  total_orders INT DEFAULT 0,
  last_order_date DATE,
  average_order_value DECIMAL(12, 2),
  
  -- Communication
  preferred_notification_channel VARCHAR(50), -- email, sms, whatsapp
  marketing_consent BOOLEAN DEFAULT true,
  
  -- Status
  is_active BOOLEAN DEFAULT true,
  
  -- Timestamps
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  
  CONSTRAINT customer_email_unique UNIQUE(nursery_id, email),
  CONSTRAINT customer_phone_unique UNIQUE(nursery_id, phone)
);

CREATE INDEX idx_customers_email ON customers(email) WHERE deleted_at IS NULL;
CREATE INDEX idx_customers_phone ON customers(phone);
```

#### orders
```sql
CREATE TABLE orders (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  order_number VARCHAR(50) NOT NULL UNIQUE, -- ORD-2025-00123
  customer_id UUID NOT NULL REFERENCES customers(id),
  nursery_id UUID NOT NULL REFERENCES nurseries(id) ON DELETE CASCADE,
  
  created_by UUID REFERENCES users(id),
  assigned_to UUID REFERENCES users(id), -- Staff member handling order
  
  order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  promise_date DATE,
  
  -- Delivery Details
  delivery_address TEXT NOT NULL,
  delivery_city VARCHAR(100),
  delivery_state VARCHAR(100),
  delivery_zip VARCHAR(20),
  delivery_location_id UUID REFERENCES locations(id),
  
  -- Financial
  subtotal DECIMAL(12, 2) NOT NULL,
  tax_amount DECIMAL(12, 2) DEFAULT 0,
  tax_rate DECIMAL(5, 2), -- 18.00 for 18% GST
  discount_amount DECIMAL(12, 2) DEFAULT 0,
  discount_reason VARCHAR(100),
  shipping_cost DECIMAL(10, 2) DEFAULT 0,
  total_amount DECIMAL(12, 2) NOT NULL,
  
  -- Status & Tracking
  status VARCHAR(50) DEFAULT 'pending', -- pending, processing, ready, shipped, delivered, cancelled, returned
  payment_status VARCHAR(50) DEFAULT 'unpaid', -- unpaid, paid, refunded, failed
  payment_method VARCHAR(50), -- card, upi, netbanking, cash_on_delivery, check
  payment_date TIMESTAMP,
  
  -- Additional Info
  special_instructions TEXT,
  customer_notes TEXT,
  internal_notes TEXT,
  
  -- Returns
  return_reason TEXT,
  returned_at TIMESTAMP,
  refund_amount DECIMAL(12, 2),
  
  -- Timestamps
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  
  CONSTRAINT order_amount_valid CHECK (total_amount >= 0)
);

CREATE INDEX idx_orders_customer ON orders(customer_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_orders_date ON orders(order_date DESC) WHERE deleted_at IS NULL;
CREATE INDEX idx_orders_status ON orders(status) WHERE deleted_at IS NULL;
CREATE INDEX idx_orders_number ON orders(order_number);
```

#### order_items
```sql
CREATE TABLE order_items (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
  product_id UUID NOT NULL REFERENCES products(id),
  
  quantity INT NOT NULL,
  unit_price DECIMAL(10, 2) NOT NULL,
  line_total DECIMAL(12, 2) NOT NULL,
  
  -- Batch Info
  batch_number VARCHAR(100),
  
  -- Fulfillment
  fulfilled_quantity INT DEFAULT 0,
  picked_at TIMESTAMP,
  packed_at TIMESTAMP,
  
  -- Timestamps
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT quantity_positive CHECK (quantity > 0),
  CONSTRAINT fulfilled_valid CHECK (fulfilled_quantity >= 0 AND fulfilled_quantity <= quantity)
);

CREATE INDEX idx_order_items_order ON order_items(order_id);
CREATE INDEX idx_order_items_product ON order_items(product_id);
```

---

### 2.5 Payment & Invoice Tables

#### payments
```sql
CREATE TABLE payments (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  order_id UUID REFERENCES orders(id) ON DELETE SET NULL,
  customer_id UUID NOT NULL REFERENCES customers(id),
  
  amount DECIMAL(12, 2) NOT NULL,
  currency VARCHAR(3) DEFAULT 'INR',
  
  payment_method VARCHAR(50) NOT NULL, -- card, upi, netbanking, cash, check
  transaction_id VARCHAR(255) UNIQUE,
  reference_number VARCHAR(100),
  
  -- Payment Gateway
  gateway_name VARCHAR(50), -- stripe, razorpay, paytm
  gateway_response JSONB, -- Full gateway response
  
  status VARCHAR(50) DEFAULT 'pending', -- pending, success, failed, refunded
  failure_reason TEXT,
  
  paid_by_user_id UUID REFERENCES users(id),
  payment_date TIMESTAMP,
  
  -- Refund Info
  refund_amount DECIMAL(12, 2) DEFAULT 0,
  refund_status VARCHAR(50), -- pending, success, failed
  refund_date TIMESTAMP,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT amount_positive CHECK (amount > 0)
);

CREATE INDEX idx_payments_customer ON payments(customer_id);
CREATE INDEX idx_payments_order ON payments(order_id);
CREATE INDEX idx_payments_status ON payments(status);
```

#### invoices
```sql
CREATE TABLE invoices (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  invoice_number VARCHAR(50) NOT NULL UNIQUE, -- INV-2025-00456
  order_id UUID NOT NULL REFERENCES orders(id),
  customer_id UUID NOT NULL REFERENCES customers(id),
  nursery_id UUID NOT NULL REFERENCES nurseries(id),
  
  invoice_date DATE NOT NULL DEFAULT CURRENT_DATE,
  due_date DATE,
  
  -- Line Items Summary
  subtotal DECIMAL(12, 2) NOT NULL,
  tax_amount DECIMAL(12, 2) DEFAULT 0,
  discount_amount DECIMAL(12, 2) DEFAULT 0,
  total_amount DECIMAL(12, 2) NOT NULL,
  
  -- Payment
  paid_amount DECIMAL(12, 2) DEFAULT 0,
  balance_due DECIMAL(12, 2) GENERATED ALWAYS AS (
    total_amount - paid_amount
  ) STORED,
  
  status VARCHAR(50) DEFAULT 'draft', -- draft, sent, viewed, paid, partial, overdue, cancelled
  
  -- Document
  pdf_url TEXT,
  email_sent_at TIMESTAMP,
  email_sent_to VARCHAR(255),
  
  notes TEXT,
  terms_and_conditions TEXT,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP
);

CREATE INDEX idx_invoices_customer ON invoices(customer_id);
CREATE INDEX idx_invoices_order ON invoices(order_id);
CREATE INDEX idx_invoices_status ON invoices(status);
```

---

### 2.6 Delivery Tables

#### deliveries
```sql
CREATE TABLE deliveries (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
  delivery_number VARCHAR(50) NOT NULL UNIQUE, -- DEL-2025-00789
  
  delivery_agent_id UUID REFERENCES users(id), -- Delivery person
  vehicle_id UUID, -- Future: Link to vehicle table
  
  scheduled_date DATE,
  estimated_delivery_time VARCHAR(50), -- e.g., "2:00 PM - 4:00 PM"
  
  -- Tracking
  actual_delivery_date DATE,
  picked_up_at TIMESTAMP,
  dispatch_at TIMESTAMP,
  
  -- Route Info
  route_sequence INT, -- For optimized routing
  current_latitude DECIMAL(10, 8),
  current_longitude DECIMAL(11, 8),
  current_location_address TEXT,
  
  -- Status
  status VARCHAR(50) DEFAULT 'pending',
  -- pending, assigned, picked_up, in_transit, out_for_delivery,
  -- delivered, failed, returned, cancelled
  
  -- Proof of Delivery
  pod_photo_urls TEXT[],
  recipient_signature_url TEXT,
  recipient_name VARCHAR(255),
  recipient_phone VARCHAR(20),
  pod_timestamp TIMESTAMP,
  
  -- Issues
  delivery_notes TEXT,
  failure_reason VARCHAR(255),
  failure_photo_urls TEXT[],
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_deliveries_order ON deliveries(order_id);
CREATE INDEX idx_deliveries_agent ON deliveries(delivery_agent_id);
CREATE INDEX idx_deliveries_status ON deliveries(status);
CREATE INDEX idx_deliveries_date ON deliveries(scheduled_date);
```

#### delivery_items
```sql
CREATE TABLE delivery_items (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  delivery_id UUID NOT NULL REFERENCES deliveries(id) ON DELETE CASCADE,
  product_id UUID NOT NULL REFERENCES products(id),
  
  quantity INT NOT NULL,
  quantity_delivered INT DEFAULT 0,
  
  status VARCHAR(50) DEFAULT 'pending', -- pending, delivered, damaged, refused
  notes TEXT,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT item_quantity_valid CHECK (quantity > 0)
);

CREATE INDEX idx_delivery_items_delivery ON delivery_items(delivery_id);
```

---

### 2.7 Supplier Tables

#### suppliers
```sql
CREATE TABLE suppliers (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  nursery_id UUID NOT NULL REFERENCES nurseries(id) ON DELETE CASCADE,
  
  name VARCHAR(255) NOT NULL,
  contact_person VARCHAR(255),
  phone VARCHAR(20),
  email VARCHAR(255),
  website VARCHAR(255),
  
  address TEXT,
  city VARCHAR(100),
  state VARCHAR(100),
  zip_code VARCHAR(20),
  
  tax_id VARCHAR(50),
  payment_terms VARCHAR(100), -- e.g., "Net 30"
  lead_time_days INT DEFAULT 7,
  
  -- Financial
  min_order_value DECIMAL(10, 2) DEFAULT 0,
  average_delivery_time INT,
  
  -- Rating
  quality_rating DECIMAL(3, 1), -- 1-5 stars
  reliability_rating DECIMAL(3, 1),
  price_rating DECIMAL(3, 1),
  
  bank_details JSONB, -- Encrypted
  
  is_active BOOLEAN DEFAULT true,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  
  CONSTRAINT supplier_email_unique UNIQUE(nursery_id, email)
);

CREATE INDEX idx_suppliers_nursery ON suppliers(nursery_id);
```

#### purchase_orders
```sql
CREATE TABLE purchase_orders (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  po_number VARCHAR(50) NOT NULL UNIQUE, -- PO-2025-00123
  supplier_id UUID NOT NULL REFERENCES suppliers(id),
  nursery_id UUID NOT NULL REFERENCES nurseries(id),
  location_id UUID NOT NULL REFERENCES locations(id),
  
  created_by UUID REFERENCES users(id),
  
  po_date DATE DEFAULT CURRENT_DATE,
  required_delivery_date DATE,
  actual_delivery_date DATE,
  
  -- Financial
  subtotal DECIMAL(12, 2) NOT NULL,
  tax_amount DECIMAL(12, 2) DEFAULT 0,
  shipping_cost DECIMAL(10, 2) DEFAULT 0,
  total_amount DECIMAL(12, 2) NOT NULL,
  
  -- Status
  status VARCHAR(50) DEFAULT 'draft', 
  -- draft, sent, acknowledged, confirmed, shipped, received, cancelled
  
  payment_status VARCHAR(50) DEFAULT 'unpaid',
  payment_date TIMESTAMP,
  
  notes TEXT,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP
);

CREATE INDEX idx_po_supplier ON purchase_orders(supplier_id);
CREATE INDEX idx_po_status ON purchase_orders(status);
```

#### purchase_order_items
```sql
CREATE TABLE purchase_order_items (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  purchase_order_id UUID NOT NULL REFERENCES purchase_orders(id) ON DELETE CASCADE,
  product_id UUID NOT NULL REFERENCES products(id),
  
  quantity INT NOT NULL,
  unit_price DECIMAL(10, 2) NOT NULL,
  line_total DECIMAL(12, 2) NOT NULL,
  
  quantity_received INT DEFAULT 0,
  received_date TIMESTAMP,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_po_items_po ON purchase_order_items(purchase_order_id);
```

---

### 2.8 Greenhouse Monitoring Tables

#### greenhouse_sensors
```sql
CREATE TABLE greenhouse_sensors (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  location_id UUID NOT NULL REFERENCES locations(id) ON DELETE CASCADE,
  
  sensor_type VARCHAR(50) NOT NULL, -- temperature, humidity, light, ph, co2, soil_moisture
  sensor_code VARCHAR(100) UNIQUE NOT NULL,
  description VARCHAR(255),
  
  -- Hardware Info
  device_id VARCHAR(100), -- IoT device identifier
  mqtt_topic VARCHAR(255),
  
  -- Calibration
  min_threshold DECIMAL(10, 2),
  max_threshold DECIMAL(10, 2),
  alert_threshold DECIMAL(10, 2),
  
  unit_of_measurement VARCHAR(20), -- °C, %, Lux, ppm, etc.
  
  is_active BOOLEAN DEFAULT true,
  last_reading DECIMAL(10, 2),
  last_reading_at TIMESTAMP,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP
);

CREATE INDEX idx_sensors_location ON greenhouse_sensors(location_id);
CREATE INDEX idx_sensors_type ON greenhouse_sensors(sensor_type);
```

#### sensor_readings
```sql
CREATE TABLE sensor_readings (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  sensor_id UUID NOT NULL REFERENCES greenhouse_sensors(id) ON DELETE CASCADE,
  
  reading_value DECIMAL(10, 2) NOT NULL,
  reading_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  is_anomaly BOOLEAN DEFAULT false, -- Flag for unusual readings
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TimescaleDB Hypertable (if using TimescaleDB extension)
-- SELECT create_hypertable('sensor_readings', 'reading_timestamp');

CREATE INDEX idx_sensor_readings_sensor_ts ON sensor_readings(
  sensor_id, reading_timestamp DESC
);
CREATE INDEX idx_sensor_readings_ts ON sensor_readings(reading_timestamp DESC);
```

#### greenhouse_alerts
```sql
CREATE TABLE greenhouse_alerts (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  sensor_id UUID NOT NULL REFERENCES greenhouse_sensors(id),
  location_id UUID NOT NULL REFERENCES locations(id),
  
  alert_type VARCHAR(50), -- threshold_exceeded, malfunction, disconnect
  severity VARCHAR(50), -- low, medium, high, critical
  
  sensor_value DECIMAL(10, 2),
  min_threshold DECIMAL(10, 2),
  max_threshold DECIMAL(10, 2),
  
  message TEXT,
  
  -- Resolution
  status VARCHAR(50) DEFAULT 'open', -- open, acknowledged, resolved, false_alarm
  resolved_at TIMESTAMP,
  resolved_by UUID REFERENCES users(id),
  resolution_notes TEXT,
  
  -- Notifications
  notification_sent BOOLEAN DEFAULT false,
  notification_sent_at TIMESTAMP,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_alerts_location ON greenhouse_alerts(location_id);
CREATE INDEX idx_alerts_sensor ON greenhouse_alerts(sensor_id);
CREATE INDEX idx_alerts_status ON greenhouse_alerts(status);
CREATE INDEX idx_alerts_severity ON greenhouse_alerts(severity);
```

---

### 2.9 Notification Tables

#### notifications
```sql
CREATE TABLE notifications (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  
  title VARCHAR(255) NOT NULL,
  message TEXT NOT NULL,
  
  notification_type VARCHAR(50), -- order, delivery, inventory, system, alert
  related_entity_type VARCHAR(50), -- Order, Delivery, Product, etc.
  related_entity_id UUID,
  
  is_read BOOLEAN DEFAULT false,
  read_at TIMESTAMP,
  
  action_url TEXT, -- Link to relevant page
  
  -- Delivery Channels
  email_sent BOOLEAN DEFAULT false,
  sms_sent BOOLEAN DEFAULT false,
  push_sent BOOLEAN DEFAULT false,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT notification_validity CHECK (
    (email_sent IS TRUE OR sms_sent IS TRUE OR push_sent IS TRUE)
  )
);

CREATE INDEX idx_notifications_user ON notifications(user_id);
CREATE INDEX idx_notifications_unread ON notifications(user_id, is_read);
```

#### notification_preferences
```sql
CREATE TABLE notification_preferences (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  
  -- Order Notifications
  order_confirmation_email BOOLEAN DEFAULT true,
  order_confirmation_sms BOOLEAN DEFAULT true,
  order_update_email BOOLEAN DEFAULT true,
  
  -- Delivery Notifications
  delivery_status_email BOOLEAN DEFAULT true,
  delivery_status_sms BOOLEAN DEFAULT true,
  
  -- Inventory Alerts
  low_stock_email BOOLEAN DEFAULT true,
  low_stock_sms BOOLEAN DEFAULT false,
  
  -- Marketing
  promotional_emails BOOLEAN DEFAULT false,
  weekly_summary BOOLEAN DEFAULT true,
  
  -- Do Not Disturb
  dnd_start_time TIME,
  dnd_end_time TIME,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT unique_user_preferences UNIQUE(user_id)
);
```

---

### 2.10 Audit & Logging

#### audit_log
```sql
CREATE TABLE audit_log (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  
  user_id UUID NOT NULL REFERENCES users(id),
  action VARCHAR(100) NOT NULL, -- create, update, delete, read, login, etc.
  
  entity_type VARCHAR(50), -- User, Order, Product, etc.
  entity_id UUID,
  
  old_values JSONB, -- Previous values (for updates)
  new_values JSONB, -- New values
  
  ip_address VARCHAR(45),
  user_agent TEXT,
  
  status VARCHAR(50) DEFAULT 'success', -- success, failed, warning
  error_message TEXT,
  
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  
  CONSTRAINT audit_log_validity CHECK (
    (action != 'create' OR old_values IS NULL) AND
    (action != 'delete' OR new_values IS NULL)
  )
);

CREATE INDEX idx_audit_log_user ON audit_log(user_id);
CREATE INDEX idx_audit_log_entity ON audit_log(entity_type, entity_id);
CREATE INDEX idx_audit_log_action ON audit_log(action);
CREATE INDEX idx_audit_log_date ON audit_log(created_at DESC);
```

---

## 3. RELATIONSHIPS & CONSTRAINTS

### 3.1 Foreign Keys

```sql
-- Users to Nurseries (One Nursery has many Users)
ALTER TABLE users ADD CONSTRAINT fk_users_nurseries
  FOREIGN KEY (nursery_id) REFERENCES nurseries(id);

-- Users to Roles
ALTER TABLE users ADD CONSTRAINT fk_users_roles
  FOREIGN KEY (role_id) REFERENCES roles(id);

-- Locations to Nurseries
ALTER TABLE locations ADD CONSTRAINT fk_locations_nurseries
  FOREIGN KEY (nursery_id) REFERENCES nurseries(id);

-- Products to Suppliers
ALTER TABLE products ADD CONSTRAINT fk_products_suppliers
  FOREIGN KEY (supplier_id) REFERENCES suppliers(id);

-- Orders to Customers
ALTER TABLE orders ADD CONSTRAINT fk_orders_customers
  FOREIGN KEY (customer_id) REFERENCES customers(id);

-- Inventory to Products and Locations
ALTER TABLE inventory ADD CONSTRAINT fk_inventory_products
  FOREIGN KEY (product_id) REFERENCES products(id);

ALTER TABLE inventory ADD CONSTRAINT fk_inventory_locations
  FOREIGN KEY (location_id) REFERENCES locations(id);
```

### 3.2 Cascading Rules

```
ON DELETE CASCADE:
- Roles → Permissions (deletion of role deletes assignments)
- Nurseries → Locations, Products, Customers, Orders, Suppliers
- Orders → Order Items, Deliveries, Payments, Invoices

ON DELETE SET NULL:
- Users (manager_id, created_by) → Allow deletion but set reference to NULL
- Products → Category, Supplier (allow category/supplier deletion)

ON DELETE RESTRICT:
- Products → Order Items (cannot delete product if used in orders)
- Locations → Inventory (cannot delete location with active inventory)
```

---

## 4. INDEXING STRATEGY

### 4.1 Performance Indexes

```sql
-- Search & Filter Indexes
CREATE INDEX idx_products_name_tsvector ON products 
  USING GIN(to_tsvector('english', name || ' ' || COALESCE(description, '')))
  WHERE is_active = true;

-- Range Query Indexes
CREATE INDEX idx_orders_date_range ON orders(order_date DESC)
  WHERE deleted_at IS NULL;

CREATE INDEX idx_sensor_readings_time_range ON sensor_readings(reading_timestamp DESC);

-- Foreign Key Indexes
CREATE INDEX idx_inventory_product_location ON inventory(product_id, location_id);

-- Status Indexes
CREATE INDEX idx_delivery_status_date ON deliveries(status, scheduled_date)
  WHERE status != 'delivered' AND status != 'cancelled';
```

### 4.2 Composite Indexes

```sql
-- For common WHERE + ORDER BY combinations
CREATE INDEX idx_orders_customer_date ON orders(customer_id, order_date DESC)
  WHERE deleted_at IS NULL;

CREATE INDEX idx_inventory_available_location ON inventory(
  location_id, 
  quantity_available DESC
) WHERE status = 'active';

CREATE INDEX idx_products_active_category ON products(
  category_id, 
  name
) WHERE is_active = true;
```

---

## 5. QUERY OPTIMIZATION

### 5.1 Common Query Patterns

```sql
-- Get customer orders with items
SELECT o.*, oi.product_id, oi.quantity, p.name
FROM orders o
JOIN order_items oi ON o.id = oi.order_id
JOIN products p ON oi.product_id = p.id
WHERE o.customer_id = $1
ORDER BY o.order_date DESC;

-- Get inventory available by location
SELECT p.sku, p.name, i.quantity_available, l.name as location
FROM inventory i
JOIN products p ON i.product_id = p.id
JOIN locations l ON i.location_id = l.id
WHERE p.nursery_id = $1 AND i.quantity_available > 0
ORDER BY p.name;

-- Get recent sensor readings
SELECT s.sensor_type, sr.reading_value, sr.reading_timestamp
FROM sensor_readings sr
JOIN greenhouse_sensors s ON sr.sensor_id = s.id
WHERE s.location_id = $1
ORDER BY sr.reading_timestamp DESC
LIMIT 100;
```

### 5.2 Query Analysis

```sql
-- Analyze query plan
EXPLAIN ANALYZE
SELECT o.id, o.order_number, COUNT(oi.id) as item_count
FROM orders o
LEFT JOIN order_items oi ON o.id = oi.order_id
WHERE o.nursery_id = $1
GROUP BY o.id, o.order_number;

-- Check index usage
SELECT schemaname, tablename, indexname
FROM pg_stat_user_indexes
WHERE idx_scan = 0; -- Unused indexes
```

---

## 6. MULTI-TENANCY IMPLEMENTATION

### 6.1 Data Isolation

```sql
-- Every query must include nursery_id
-- Example: Only users and their nursery's data
SELECT *
FROM orders
WHERE customer_id = $1 AND nursery_id = $2;

-- RLS (Row Level Security) Policy (PostgreSQL)
CREATE POLICY orders_isolation ON orders
  USING (nursery_id = current_setting('app.current_nursery_id')::uuid)
  WITH CHECK (nursery_id = current_setting('app.current_nursery_id')::uuid);
```

---

## 7. BACKUP & RECOVERY

### 7.1 Backup Strategy

```sql
-- Full backup (daily)
pg_dump nursery_db > nursery_db_$(date +%Y%m%d).sql

-- Point-in-time recovery
pg_dump --format=custom nursery_db > nursery_db.dump

-- Incremental backups using WAL files
archive_command = 'cp %p /backup/wal_archive/%f'
```

---

## APPENDICES

### A. Data Types Reference

```
UUID: gen_random_uuid()
DECIMAL: DECIMAL(10,2) for currency
TIMESTAMP: CURRENT_TIMESTAMP
DATE: CURRENT_DATE
JSONB: For flexible structures
Array: TEXT[], DECIMAL[]
ENUM: VARCHAR with CHECK constraint
```

### B. Naming Conventions

```
Tables: snake_case, plural (orders, customers)
Columns: snake_case (order_date, customer_id)
Indexes: idx_{table}_{columns}
Constraints: {type}_{table}_{columns}
Foreign Keys: fk_{table}_{referenced_table}
```

---

**Document Version Control:**
| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 0.1 | May 2025 | Database Team | Initial draft |
| 1.0 | May 2025 | Database Team | Ready for implementation |

