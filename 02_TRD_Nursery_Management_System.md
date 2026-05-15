# TECHNICAL REQUIREMENTS DOCUMENT (TRD)
## Nursery Management System
**Version:** 1.0  
**Date:** May 2025  
**Status:** Ready for Development  
**Document Owner:** Engineering Lead

---

## 1. SYSTEM ARCHITECTURE

### 1.1 High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         CLIENT LAYER                             │
├──────────────────────┬──────────────────────┬──────────────────┤
│  Web App (React)     │  Mobile App (React   │  Admin Dashboard │
│  TypeScript          │  Native/Flutter)     │  (React)         │
│  Tailwind CSS        │  Offline Support     │                  │
└──────────────────────┴──────────────────────┴──────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                     API GATEWAY LAYER                            │
│  (AWS API Gateway / Kong)                                       │
│  - Request routing                                              │
│  - Rate limiting                                                │
│  - CORS handling                                                │
│  - Request validation                                           │
└─────────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                   MICROSERVICES LAYER                            │
├────────────────┬─────────────┬──────────┬──────────────────────┤
│ Auth Service   │  Inventory  │  Order   │  Greenhouse Svc      │
│ (JWT, OAuth2)  │  Service    │  Service │  (IoT Integration)   │
├────────────────┼─────────────┼──────────┼──────────────────────┤
│ Customer Svc   │  Payment    │  Delivery│  Analytics Service   │
│ (CRM)          │  Service    │  Service │  (ML/Analytics)      │
├────────────────┼─────────────┼──────────┼──────────────────────┤
│ Supplier Svc   │  Notification│ Reporting│  File Service       │
│ (Vendor Mgmt)  │  Service     │ Service  │  (S3, Media)        │
└────────────────┴─────────────┴──────────┴──────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│                    DATA LAYER                                    │
├──────────────────┬──────────────────┬──────────────────────────┤
│  PostgreSQL      │  Redis Cache     │  Elasticsearch           │
│  (Primary DB)    │  (Session, Cache)│  (Search & Logs)        │
├──────────────────┼──────────────────┼──────────────────────────┤
│  AWS S3          │  TimescaleDB     │  Message Queue           │
│  (File Storage)  │  (Time Series)   │  (Kafka/RabbitMQ)       │
└──────────────────┴──────────────────┴──────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────────┐
│              INTEGRATION & MONITORING LAYER                      │
├──────────────────┬──────────────────┬──────────────────────────┤
│  Payment Gateway │  SMS/Email       │  Prometheus              │
│  (Stripe, Razorpay)│ (Twilio, AWS SES)│ (Metrics)              │
├──────────────────┼──────────────────┼──────────────────────────┤
│  Logistics API   │  IoT Hub         │  Grafana/Datadog        │
│  (Shipping)      │  (Sensor Data)   │  (Visualization)        │
└──────────────────┴──────────────────┴──────────────────────────┘
```

### 1.2 Technology Stack

| Layer | Technology | Justification |
|-------|-----------|---------------|
| **Frontend Web** | React 18, TypeScript, Tailwind CSS | Modern, scalable, strong typing |
| **Frontend Mobile** | React Native / Flutter | Cross-platform, shared codebase |
| **Backend** | Spring Boot 3.x | Enterprise-grade, microservices ready |
| **Database** | PostgreSQL 15+ | Relational, ACID compliance, JSON support |
| **Cache** | Redis 7+ | High-performance, session management |
| **Search** | Elasticsearch 8+ | Full-text search, analytics |
| **Message Queue** | Apache Kafka / RabbitMQ | Event streaming, async processing |
| **File Storage** | AWS S3 | Scalable, reliable, cost-effective |
| **Container** | Docker | Consistent deployment |
| **Orchestration** | Kubernetes / AWS ECS | Auto-scaling, high availability |
| **API Gateway** | AWS API Gateway / Kong | API management, rate limiting |
| **Monitoring** | Prometheus + Grafana | Metrics, visualization, alerting |
| **Logging** | ELK Stack / CloudWatch | Centralized logging |
| **CI/CD** | GitHub Actions / Jenkins | Automated pipeline |

---

## 2. FRONTEND ARCHITECTURE

### 2.1 React Application Structure

```
src/
├── components/
│   ├── common/
│   │   ├── Header.tsx
│   │   ├── Sidebar.tsx
│   │   ├── Footer.tsx
│   │   ├── Loader.tsx
│   │   └── ErrorBoundary.tsx
│   ├── auth/
│   │   ├── LoginForm.tsx
│   │   ├── RegisterForm.tsx
│   │   └── MFASetup.tsx
│   ├── dashboard/
│   │   ├── AdminDashboard.tsx
│   │   ├── OwnerDashboard.tsx
│   │   ├── ManagerDashboard.tsx
│   │   └── CustomerDashboard.tsx
│   ├── inventory/
│   │   ├── InventoryList.tsx
│   │   ├── InventoryForm.tsx
│   │   ├── StockMovement.tsx
│   │   └── LowStockAlerts.tsx
│   ├── orders/
│   │   ├── OrderList.tsx
│   │   ├── OrderForm.tsx
│   │   ├── OrderDetails.tsx
│   │   └── OrderTracking.tsx
│   ├── greenhouse/
│   │   ├── GreenhouseMonitor.tsx
│   │   ├── SensorDashboard.tsx
│   │   └── AlertPanel.tsx
│   └── reports/
│       ├── ReportBuilder.tsx
│       ├── SalesReport.tsx
│       └── AnalyticsChart.tsx
├── pages/
│   ├── LoginPage.tsx
│   ├── DashboardPage.tsx
│   ├── InventoryPage.tsx
│   ├── OrdersPage.tsx
│   ├── CustomersPage.tsx
│   └── SettingsPage.tsx
├── services/
│   ├── api/
│   │   ├── authService.ts
│   │   ├── inventoryService.ts
│   │   ├── orderService.ts
│   │   ├── customerService.ts
│   │   └── commonService.ts
│   ├── cache/
│   │   └── cacheService.ts
│   └── storage/
│       └── localStorageService.ts
├── hooks/
│   ├── useAuth.ts
│   ├── useInventory.ts
│   ├── useOrders.ts
│   ├── useFetch.ts
│   └── useLocalStorage.ts
├── context/
│   ├── AuthContext.tsx
│   ├── ThemeContext.tsx
│   └── NotificationContext.tsx
├── utils/
│   ├── validators.ts
│   ├── formatters.ts
│   ├── dateUtils.ts
│   └── helpers.ts
├── types/
│   ├── index.ts
│   ├── auth.ts
│   ├── inventory.ts
│   ├── orders.ts
│   └── common.ts
├── styles/
│   ├── globals.css
│   ├── variables.css
│   └── responsive.css
├── App.tsx
└── index.tsx
```

### 2.2 State Management

**Tool:** Redux Toolkit

```typescript
store/
├── slices/
│   ├── authSlice.ts         // User auth state
│   ├── inventorySlice.ts    // Inventory state
│   ├── orderSlice.ts        // Orders state
│   ├── customerSlice.ts     // Customer state
│   ├── uiSlice.ts           // UI state (modals, toasts)
│   └── settingsSlice.ts     // User settings
├── hooks/
│   ├── useAppDispatch.ts
│   └── useAppSelector.ts
└── store.ts
```

### 2.3 API Integration

```typescript
// apiClient.ts - Axios interceptor for API calls
- Base URL configuration
- JWT token injection
- Request/response interceptors
- Error handling
- Request timeout (30s)
- Retry logic (exponential backoff)
```

### 2.4 Performance Optimization

- Code splitting with React.lazy()
- Image optimization with next-gen formats
- Lazy loading for lists and infinite scroll
- Memoization with React.memo and useMemo
- Bundle size monitoring
- Service Worker for offline functionality

---

## 3. BACKEND ARCHITECTURE

### 3.1 Spring Boot Microservices

#### Service 1: Auth Service
```
Purpose: User authentication & authorization
Port: 8001
Dependencies:
- PostgreSQL (users, roles, permissions)
- Redis (session tokens)
- JWT library

Key Endpoints:
- POST /api/v1/auth/register
- POST /api/v1/auth/login
- POST /api/v1/auth/refresh-token
- POST /api/v1/auth/logout
- GET /api/v1/auth/profile
- POST /api/v1/auth/mfa/setup
- POST /api/v1/auth/mfa/verify
```

#### Service 2: Inventory Service
```
Purpose: Inventory management
Port: 8002
Dependencies:
- PostgreSQL (products, stock, movements)
- Redis (cache for product data)
- Kafka (publish stock changes)

Key Endpoints:
- GET /api/v1/inventory/products
- POST /api/v1/inventory/products
- PUT /api/v1/inventory/products/{id}
- GET /api/v1/inventory/stock/{location}
- POST /api/v1/inventory/stock-movement
- GET /api/v1/inventory/low-stock
- POST /api/v1/inventory/bulk-import
```

#### Service 3: Order Service
```
Purpose: Order management & processing
Port: 8003
Dependencies:
- PostgreSQL (orders, order items)
- Inventory Service (check stock)
- Payment Service (process payments)
- Notification Service (send confirmations)
- Kafka (publish order events)

Key Endpoints:
- POST /api/v1/orders
- GET /api/v1/orders/{id}
- GET /api/v1/orders (with filters)
- PUT /api/v1/orders/{id}
- DELETE /api/v1/orders/{id}
- POST /api/v1/orders/{id}/cancel
- GET /api/v1/orders/{id}/tracking
```

#### Service 4: Customer Service
```
Purpose: Customer relationship management
Port: 8004
Dependencies:
- PostgreSQL (customers, preferences, history)
- Redis (cache for frequent customers)

Key Endpoints:
- POST /api/v1/customers
- GET /api/v1/customers/{id}
- PUT /api/v1/customers/{id}
- DELETE /api/v1/customers/{id}
- GET /api/v1/customers/{id}/orders
- GET /api/v1/customers/{id}/preferences
- POST /api/v1/customers/{id}/loyalty-points
```

#### Service 5: Payment Service
```
Purpose: Payment processing & billing
Port: 8005
Dependencies:
- PostgreSQL (transactions, invoices)
- Payment Gateway (Stripe, Razorpay)
- Notification Service

Key Endpoints:
- POST /api/v1/payments/process
- GET /api/v1/payments/{id}
- POST /api/v1/invoices
- GET /api/v1/invoices/{id}
- GET /api/v1/invoices/customer/{customerId}
```

#### Service 6: Delivery Service
```
Purpose: Delivery management & tracking
Port: 8006
Dependencies:
- PostgreSQL (deliveries, routes)
- Location Service (GPS data)
- Notification Service (delivery updates)

Key Endpoints:
- POST /api/v1/deliveries
- GET /api/v1/deliveries/{id}
- PUT /api/v1/deliveries/{id}/status
- GET /api/v1/deliveries/tracking/{id}
- POST /api/v1/deliveries/{id}/pod (Proof of Delivery)
```

#### Service 7: Notification Service
```
Purpose: Multi-channel notifications
Port: 8007
Dependencies:
- AWS SES (Email)
- Twilio (SMS)
- Firebase (Push notifications)
- Redis (Notification queue)

Key Endpoints:
- POST /api/v1/notifications/email
- POST /api/v1/notifications/sms
- POST /api/v1/notifications/push
- GET /api/v1/notifications/user/{userId}
- PUT /api/v1/notifications/{id}/read
```

#### Service 8: Supplier Service
```
Purpose: Supplier management
Port: 8008
Dependencies:
- PostgreSQL (suppliers, catalogs, orders)

Key Endpoints:
- POST /api/v1/suppliers
- GET /api/v1/suppliers/{id}
- GET /api/v1/suppliers/{id}/catalog
- POST /api/v1/purchase-orders
- GET /api/v1/purchase-orders/{id}
```

#### Service 9: Greenhouse Service
```
Purpose: Greenhouse monitoring & automation
Port: 8009
Dependencies:
- TimescaleDB (time-series sensor data)
- MQTT Broker (IoT communication)
- Kafka (sensor data events)

Key Endpoints:
- GET /api/v1/greenhouse/sensors
- POST /api/v1/greenhouse/sensor-reading
- GET /api/v1/greenhouse/readings/{sensorId}
- GET /api/v1/greenhouse/alerts
- POST /api/v1/greenhouse/automation-rule
```

#### Service 10: Analytics Service
```
Purpose: Analytics & reporting
Port: 8010
Dependencies:
- Elasticsearch (analytics data)
- Kafka (consume events)
- TimescaleDB (historical analysis)

Key Endpoints:
- GET /api/v1/analytics/sales
- GET /api/v1/analytics/inventory
- GET /api/v1/analytics/customers
- GET /api/v1/analytics/delivery-performance
- POST /api/v1/reports/generate
```

### 3.2 Service Communication

```
Synchronous (REST/gRPC):
- Order Service → Inventory Service (check stock)
- Order Service → Payment Service (process payment)
- Delivery Service → Order Service (update status)

Asynchronous (Kafka/Message Queue):
- Order Service → Notification Service (order confirmation)
- Inventory Service → Analytics Service (stock changes)
- Delivery Service → Analytics Service (delivery metrics)
- Greenhouse Service → Analytics Service (sensor readings)
- Order Service → Inventory Service (reduce stock)
```

### 3.3 Spring Boot Configuration

```yaml
# application.yml
spring:
  application:
    name: nursery-management-system
  datasource:
    url: jdbc:postgresql://localhost:5432/nursery_db
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQL15Dialect
        format_sql: true
  redis:
    host: ${REDIS_HOST:localhost}
    port: ${REDIS_PORT:6379}
  kafka:
    bootstrap-servers: ${KAFKA_SERVERS}
    producer:
      acks: all
      retries: 3
  security:
    jwt:
      secret: ${JWT_SECRET}
      expiration: 86400000

server:
  port: ${SERVICE_PORT:8080}
  servlet:
    context-path: /api/v1
```

---

## 4. DATABASE DESIGN

### 4.1 Database Schema Overview

```
Core Tables:
- users (authentication)
- roles (permission management)
- permissions (RBAC)
- nurseries (multi-tenant)
- locations (physical locations)

Business Tables:
- products (plant catalog)
- inventory (stock levels)
- inventory_movements (history)
- customers (CRM)
- orders (order management)
- order_items (line items)
- payments (payment records)
- invoices (billing)

Operations:
- deliveries (delivery tracking)
- delivery_items (delivery line items)
- suppliers (vendor management)
- purchase_orders (supplier orders)

Monitoring:
- greenhouse_sensors (IoT devices)
- sensor_readings (time-series data)
- greenhouse_alerts (alert management)
- plant_health_logs (plant tracking)
```

### 4.2 Key Tables

```sql
-- Users table
CREATE TABLE users (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  email VARCHAR(255) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  phone VARCHAR(20),
  role_id UUID NOT NULL REFERENCES roles(id),
  nursery_id UUID NOT NULL REFERENCES nurseries(id),
  is_active BOOLEAN DEFAULT true,
  mfa_enabled BOOLEAN DEFAULT false,
  mfa_secret VARCHAR(255),
  last_login_at TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP -- Soft delete
);

-- Products table
CREATE TABLE products (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  nursery_id UUID NOT NULL REFERENCES nurseries(id),
  sku VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  category_id UUID REFERENCES categories(id),
  unit_price DECIMAL(10, 2) NOT NULL,
  cost_price DECIMAL(10, 2),
  quantity_unit VARCHAR(50), -- "piece", "kg", "liter"
  min_stock_level INT DEFAULT 10,
  max_stock_level INT DEFAULT 1000,
  supplier_id UUID REFERENCES suppliers(id),
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP
);

-- Inventory table
CREATE TABLE inventory (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  product_id UUID NOT NULL REFERENCES products(id),
  location_id UUID NOT NULL REFERENCES locations(id),
  quantity_on_hand INT NOT NULL DEFAULT 0,
  quantity_reserved INT NOT NULL DEFAULT 0,
  quantity_available INT GENERATED ALWAYS AS (quantity_on_hand - quantity_reserved) STORED,
  batch_number VARCHAR(100),
  expiry_date DATE,
  last_counted_at TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE(product_id, location_id, batch_number)
);

-- Orders table
CREATE TABLE orders (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  order_number VARCHAR(50) NOT NULL UNIQUE,
  customer_id UUID NOT NULL REFERENCES customers(id),
  nursery_id UUID NOT NULL REFERENCES nurseries(id),
  order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  delivery_address TEXT NOT NULL,
  delivery_city VARCHAR(100),
  delivery_state VARCHAR(100),
  delivery_zip VARCHAR(20),
  subtotal DECIMAL(12, 2) NOT NULL,
  tax_amount DECIMAL(12, 2) DEFAULT 0,
  discount_amount DECIMAL(12, 2) DEFAULT 0,
  total_amount DECIMAL(12, 2) NOT NULL,
  status VARCHAR(50) DEFAULT 'pending', -- pending, processing, ready, shipped, delivered, cancelled
  payment_status VARCHAR(50) DEFAULT 'unpaid', -- unpaid, paid, refunded
  payment_method VARCHAR(50), -- credit_card, upi, net_banking, cash
  special_instructions TEXT,
  created_by UUID REFERENCES users(id),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP
);

-- Customers table
CREATE TABLE customers (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  nursery_id UUID NOT NULL REFERENCES nurseries(id),
  email VARCHAR(255) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100),
  customer_type VARCHAR(50), -- retail, wholesale, corporate
  address TEXT,
  city VARCHAR(100),
  state VARCHAR(100),
  zip_code VARCHAR(20),
  gst_number VARCHAR(50),
  loyalty_points INT DEFAULT 0,
  total_spent DECIMAL(12, 2) DEFAULT 0,
  total_orders INT DEFAULT 0,
  last_order_date DATE,
  is_active BOOLEAN DEFAULT true,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  UNIQUE(nursery_id, email)
);

-- Sensor readings (time-series)
CREATE TABLE sensor_readings (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  sensor_id UUID NOT NULL REFERENCES greenhouse_sensors(id),
  reading_value FLOAT NOT NULL,
  reading_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_sensor_readings_sensor_timestamp 
  ON sensor_readings(sensor_id, reading_timestamp DESC);
```

### 4.3 Indexing Strategy

```sql
-- Performance indexes
CREATE INDEX idx_users_email ON users(email) WHERE deleted_at IS NULL;
CREATE INDEX idx_users_nursery ON users(nursery_id) WHERE is_active = true;
CREATE INDEX idx_products_sku ON products(sku) WHERE is_active = true;
CREATE INDEX idx_inventory_product_location ON inventory(product_id, location_id);
CREATE INDEX idx_orders_customer ON orders(customer_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_orders_date ON orders(order_date DESC) WHERE deleted_at IS NULL;
CREATE INDEX idx_orders_status ON orders(status) WHERE deleted_at IS NULL;
CREATE INDEX idx_customers_email ON customers(email) WHERE deleted_at IS NULL;
CREATE INDEX idx_sensor_readings_time ON sensor_readings(reading_timestamp DESC);

-- Foreign key indexes
CREATE INDEX idx_orders_nursery ON orders(nursery_id);
CREATE INDEX idx_inventory_movements_product ON inventory_movements(product_id);
```

### 4.4 Query Optimization

- Use prepared statements to prevent SQL injection
- Implement connection pooling (HikariCP)
- Use database views for complex queries
- Implement query caching in Redis
- Regular query performance analysis

---

## 5. API DESIGN

### 5.1 RESTful API Standards

```
Base URL: https://api.nurserysystem.com/api/v1

Request Format:
- Headers:
  Authorization: Bearer {jwt_token}
  Content-Type: application/json
  
Response Format:
{
  "success": true,
  "data": {},
  "message": "Success message",
  "errors": null,
  "timestamp": "2025-05-13T10:30:00Z",
  "path": "/api/v1/..."
}

Error Response:
{
  "success": false,
  "data": null,
  "message": "Error description",
  "errors": [
    {
      "field": "email",
      "message": "Invalid email format"
    }
  ],
  "timestamp": "2025-05-13T10:30:00Z",
  "path": "/api/v1/..."
}
```

### 5.2 API Endpoints (Comprehensive)

#### Authentication APIs
```
POST /auth/register
  Body: { email, password, firstName, lastName, nurseryName }
  Response: { userId, token, refreshToken }

POST /auth/login
  Body: { email, password }
  Response: { token, refreshToken, user }

POST /auth/refresh-token
  Body: { refreshToken }
  Response: { token, refreshToken }

POST /auth/logout
  Headers: Authorization
  Response: { success: true }

GET /auth/profile
  Headers: Authorization
  Response: { user }

POST /auth/mfa/setup
  Headers: Authorization
  Response: { qrCode, secret }

POST /auth/mfa/verify
  Body: { code }
  Response: { success: true, backup_codes }
```

#### Inventory APIs
```
GET /inventory/products
  Query: { page, size, search, category, status }
  Response: { products: [], total, page, size }

POST /inventory/products
  Body: { sku, name, description, category, price, supplier }
  Response: { product }

PUT /inventory/products/{id}
  Body: { name, description, price, ... }
  Response: { product }

GET /inventory/stock/{locationId}
  Query: { page, size }
  Response: { stocks: [] }

POST /inventory/stock-movement
  Body: { productId, locationId, type, quantity, reason }
  Response: { movement }

GET /inventory/low-stock
  Response: { products: [] }

POST /inventory/bulk-import
  Body: FormData with CSV file
  Response: { importId, status, results }

GET /inventory/batch/{batchId}
  Response: { batch details }
```

#### Order APIs
```
POST /orders
  Body: { customerId, items: [{productId, quantity}], deliveryAddress }
  Response: { orderId, orderNumber, totalAmount }

GET /orders/{id}
  Response: { order with items, customer, delivery }

GET /orders
  Query: { status, customerId, dateFrom, dateTo, page }
  Response: { orders: [], total }

PUT /orders/{id}
  Body: { status, notes, ... }
  Response: { order }

POST /orders/{id}/cancel
  Body: { reason }
  Response: { order with status: cancelled }

GET /orders/{id}/tracking
  Response: { orderNumber, status, deliveryStatus, location }

GET /orders/{id}/invoice
  Response: PDF invoice
```

#### Customer APIs
```
POST /customers
  Body: { email, phone, firstName, lastName, address, type }
  Response: { customer }

GET /customers/{id}
  Response: { customer with orders, preferences, loyalty }

GET /customers
  Query: { search, type, page }
  Response: { customers: [], total }

PUT /customers/{id}
  Body: { firstName, lastName, phone, address, ... }
  Response: { customer }

GET /customers/{id}/orders
  Query: { page, status }
  Response: { orders: [], total }

POST /customers/{id}/loyalty-points
  Body: { points, reason }
  Response: { customer with updated loyalty_points }
```

#### Payment APIs
```
POST /payments/process
  Body: { orderId, amount, paymentMethod, paymentDetails }
  Response: { transactionId, status, message }

GET /payments/{id}
  Response: { payment details }

POST /invoices
  Body: { orderId }
  Response: { invoiceId, invoiceNumber, pdfUrl }

GET /invoices/{id}
  Response: { invoice details or PDF }

GET /customers/{id}/invoices
  Query: { page, status }
  Response: { invoices: [], total }
```

#### Delivery APIs
```
POST /deliveries
  Body: { orderId, deliveryAgentId, estimatedDeliveryDate }
  Response: { deliveryId, trackingNumber }

GET /deliveries/{id}
  Response: { delivery with route, agent, status }

PUT /deliveries/{id}/status
  Body: { status, location, notes }
  Response: { delivery updated }

GET /deliveries/{id}/tracking
  Response: { trackingNumber, status, currentLocation, estimatedTime }

POST /deliveries/{id}/pod
  Body: { photo, signature, recipientName }
  Response: { delivery marked complete }

GET /deliveries
  Query: { status, agentId, dateFrom, dateTo }
  Response: { deliveries: [] }
```

#### Reports APIs
```
POST /reports/generate
  Body: { reportType, filters, format }
  Response: { reportId, downloadUrl }

GET /reports/{id}
  Response: File download (PDF/CSV/Excel)

GET /analytics/sales
  Query: { dateFrom, dateTo, groupBy }
  Response: { salesData, chart }

GET /analytics/inventory
  Query: { locationId, status }
  Response: { inventoryAnalysis }

GET /analytics/customers
  Query: { metric, dateFrom, dateTo }
  Response: { customerAnalytics }

GET /analytics/delivery-performance
  Query: { dateFrom, dateTo, agentId }
  Response: { deliveryMetrics }
```

### 5.3 HTTP Status Codes

| Code | Meaning | Example |
|------|---------|---------|
| 200 | Success | Successful GET, PUT |
| 201 | Created | Successful POST creating resource |
| 204 | No Content | Successful DELETE |
| 400 | Bad Request | Invalid input parameters |
| 401 | Unauthorized | Missing or invalid token |
| 403 | Forbidden | User lacks permission |
| 404 | Not Found | Resource doesn't exist |
| 409 | Conflict | Duplicate entry (email, SKU) |
| 429 | Too Many Requests | Rate limit exceeded |
| 500 | Server Error | Unexpected error |
| 503 | Service Unavailable | Service down for maintenance |

### 5.4 API Versioning

- Current version: v1
- Future versions: v2, v3 (maintain backward compatibility for 2 versions)
- Deprecation headers: `Sunset: <date>`, `Deprecation: true`

---

## 6. AUTHENTICATION FLOW

### 6.1 JWT Token Flow

```
1. User Registration
   POST /auth/register
   ├── Validate input
   ├── Hash password (bcrypt)
   ├── Create user in DB
   └── Return success

2. User Login
   POST /auth/login
   ├── Validate credentials
   ├── Verify password hash
   ├── Generate JWT token
   │  └── Header: { alg: HS256, type: JWT }
   │      Payload: { userId, email, role, nurseryId, iat, exp }
   │      Signature: HS256(header.payload, SECRET)
   ├── Generate refresh token
   └── Return tokens with 1-hour expiry

3. Protected API Call
   GET /api/v1/orders
   Header: Authorization: Bearer {jwt_token}
   ├── Verify JWT signature
   ├── Check token expiry
   ├── Extract claims (userId, role)
   ├── Authorize request
   └── Process request

4. Token Refresh
   POST /auth/refresh-token
   Body: { refreshToken }
   ├── Validate refresh token
   ├── Issue new JWT
   ├── Blacklist old JWT (optional)
   └── Return new token

5. Logout
   POST /auth/logout
   ├── Blacklist token
   ├── Delete refresh token
   └── Clear session
```

### 6.2 MFA (Multi-Factor Authentication)

```
1. Setup MFA
   POST /auth/mfa/setup
   ├── Generate TOTP secret
   ├── Create QR code
   ├── Display backup codes
   └── Return for user confirmation

2. Enable MFA
   POST /auth/mfa/verify
   Body: { code }
   ├── Verify TOTP code
   ├── Store backup codes
   ├── Mark MFA enabled
   └── Return success

3. Login with MFA
   POST /auth/login
   ├── Validate credentials
   ├── If MFA enabled:
   │  ├── Send MFA prompt
   │  └── Require TOTP/backup code
   └── Issue token after verification
```

### 6.3 OAuth2 Integration (Optional)

```
Providers: Google, Microsoft, GitHub

Flow:
1. User clicks "Sign in with Google"
2. Redirect to Google OAuth endpoint
3. User grants permission
4. Google redirects with auth code
5. Exchange code for access token
6. Fetch user info from Google
7. Create/link user in system
8. Issue JWT token
9. Redirect to dashboard
```

---

## 7. AUTHORIZATION MODEL (RBAC)

### 7.1 Role Hierarchy

```
┌─────────────────────────────────────────────┐
│              SUPER_ADMIN                    │
│  (Full system access, manage all nurseries) │
└──────────────────┬──────────────────────────┘
                   │
┌──────────────────┴──────────────────────────┐
│           NURSERY_OWNER                     │
│  (Full access to their nursery)             │
├──────────────┬──────────────┬───────────────┤
│              │              │               │
▼              ▼              ▼               ▼
MANAGER      STAFF        CUSTOMER      SUPPLIER
(Team coord) (Operations) (Web/App)     (Vendor)
```

### 7.2 Permission Matrix

```typescript
const rolePermissions = {
  SUPER_ADMIN: [
    'manage_users',
    'manage_nurseries',
    'manage_roles',
    'view_all_analytics',
    'system_configuration'
  ],
  
  NURSERY_OWNER: [
    'manage_inventory',
    'manage_orders',
    'manage_customers',
    'manage_staff',
    'manage_suppliers',
    'manage_billing',
    'manage_reports',
    'greenhouse_control'
  ],
  
  MANAGER: [
    'view_inventory',
    'manage_orders',
    'manage_deliveries',
    'manage_staff_tasks',
    'view_reports',
    'manage_customers'
  ],
  
  STAFF: [
    'view_inventory',
    'process_orders',
    'update_stock',
    'view_assigned_tasks',
    'manage_greenhouse'
  ],
  
  DELIVERY_AGENT: [
    'view_assigned_deliveries',
    'update_delivery_status',
    'capture_pod',
    'view_delivery_route'
  ],
  
  CUSTOMER: [
    'view_own_orders',
    'track_orders',
    'manage_profile',
    'view_products',
    'place_orders'
  ],
  
  SUPPLIER: [
    'view_purchase_orders',
    'update_order_status',
    'view_own_products',
    'manage_pricing'
  ]
};
```

### 7.3 Access Control Implementation

```java
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
  
  @GetMapping
  @PreAuthorize("hasAnyRole('NURSERY_OWNER', 'MANAGER', 'STAFF')")
  public ResponseEntity<?> getOrders() {
    // Access control enforced by Spring Security
  }
  
  @PostMapping
  @PreAuthorize("hasRole('NURSERY_OWNER') or hasRole('MANAGER')")
  public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
    // Only owner and manager can create
  }
  
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('NURSERY_OWNER')")
  public ResponseEntity<?> deleteOrder(@PathVariable UUID id) {
    // Only owner can delete
  }
}
```

---

## 8. MICROSERVICES BREAKDOWN

### 8.1 Service Dependencies Graph

```
Auth Service
├── Requires: PostgreSQL, JWT Library
└── Called by: All services

Inventory Service
├── Requires: PostgreSQL, Redis, Kafka
└── Called by: Order Service, Analytics Service

Order Service
├── Requires: PostgreSQL, Kafka
├── Depends on: Inventory Service, Payment Service, Notification Service
└── Called by: Delivery Service, Analytics Service

Payment Service
├── Requires: PostgreSQL, Payment Gateway API
└── Called by: Order Service

Notification Service
├── Requires: Email Service (SES), SMS Service (Twilio)
└── Called by: Order, Delivery, Customer Services

Delivery Service
├── Requires: PostgreSQL, Location API
├── Depends on: Order Service, Notification Service
└── Called by: Analytics Service

Customer Service
├── Requires: PostgreSQL, Redis
└── Called by: Order Service, Analytics Service

Supplier Service
├── Requires: PostgreSQL
└── Called by: Inventory Service

Greenhouse Service
├── Requires: TimescaleDB, MQTT Broker, IoT Integration
└── Called by: Analytics Service, Notification Service

Analytics Service
├── Requires: Elasticsearch, TimescaleDB
├── Depends on: Kafka (consumes all events)
└── Called by: All services
```

### 8.2 Inter-Service Communication

```
Synchronous (REST):
- Order Service → Inventory Service (check availability)
- Order Service → Payment Service (process payment)
- Delivery Service → Order Service (update status)

Asynchronous (Kafka):
- Inventory Service → Topic: inventory.events
  Consumers: Analytics, Order Service
  
- Order Service → Topic: order.events
  Consumers: Notification, Delivery, Analytics
  
- Delivery Service → Topic: delivery.events
  Consumers: Notification, Analytics
  
- Greenhouse Service → Topic: sensor.events
  Consumers: Analytics, Notification
```

---

## 9. DEPLOYMENT ARCHITECTURE

### 9.1 AWS Infrastructure

```
┌──────────────────────────────────────────────────────────────┐
│                        INTERNET                              │
│                        ▼                                     │
│               ┌─────────────────┐                            │
│               │  CloudFront CDN │                            │
│               └────────┬────────┘                            │
│                        ▼                                     │
│         ┌─────────────────────────────┐                     │
│         │   AWS Application Load      │                     │
│         │        Balancer             │                     │
│         │  (SSL/TLS Termination)      │                     │
│         └──────┬──────────────────────┘                     │
│              ▼   ▼   ▼                                      │
│         ┌──────────────────────────┐                       │
│         │   AWS ECS Cluster        │                       │
│         │  (Kubernetes Alternative)│                       │
│         ├──────────────────────────┤                       │
│         │ Task 1: Auth Service     │                       │
│         │ Task 2: Inventory Svc    │                       │
│         │ Task 3: Order Service    │                       │
│         │ Task 4: Payment Service  │                       │
│         │ Task 5: Delivery Service │                       │
│         │ ... more services        │                       │
│         └──────────┬───────────────┘                       │
│                    ▼                                        │
│         ┌──────────────────────────┐                       │
│         │   RDS PostgreSQL         │                       │
│         │   (Multi-AZ, Replica)    │                       │
│         └──────────┬───────────────┘                       │
│                    ▼                                        │
│         ┌──────────────────────────┐                       │
│         │   ElastiCache (Redis)    │                       │
│         │   (Session, Cache)       │                       │
│         └──────────────────────────┘                       │
│                                                            │
│         ┌──────────────────────────┐                       │
│         │   MSK (Kafka)            │                       │
│         │   (Event Streaming)      │                       │
│         └──────────────────────────┘                       │
│                                                            │
│         ┌──────────────────────────┐                       │
│         │   S3 Buckets             │                       │
│         │   (Images, Documents)    │                       │
│         └──────────────────────────┘                       │
│                                                            │
│         ┌──────────────────────────┐                       │
│         │   Elasticsearch          │                       │
│         │   (Search & Logs)        │                       │
│         └──────────────────────────┘                       │
└──────────────────────────────────────────────────────────────┘
```

### 9.2 Container Deployment

```dockerfile
# Dockerfile for Spring Boot service
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /build
COPY . .
RUN ./gradlew clean build -x test

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /build/build/libs/*.jar app.jar

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

EXPOSE 8080
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]
```

```yaml
# docker-compose.yml for local development
version: '3.9'

services:
  postgres:
    image: postgres:15
    environment:
      POSTGRES_DB: nursery_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    volumes:
      - postgres_data:/var/lib/postgresql/data
    ports:
      - "5432:5432"

  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
    ports:
      - "9092:9092"

  auth-service:
    build: ./services/auth-service
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/nursery_db
      SPRING_REDIS_HOST: redis
      SPRING_KAFKA_BOOTSTRAP_SERVERS: kafka:29092
    ports:
      - "8001:8080"
    depends_on:
      - postgres
      - redis
      - kafka

  # Additional services...

volumes:
  postgres_data:
```

### 9.3 Kubernetes Deployment

```yaml
# deployment.yaml for Auth Service
apiVersion: apps/v1
kind: Deployment
metadata:
  name: auth-service
  namespace: nursery-system
spec:
  replicas: 3
  selector:
    matchLabels:
      app: auth-service
  template:
    metadata:
      labels:
        app: auth-service
    spec:
      containers:
      - name: auth-service
        image: nursery-system/auth-service:1.0.0
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_DATASOURCE_URL
          valueFrom:
            configMapKeyRef:
              name: auth-service-config
              key: db-url
        - name: SPRING_DATASOURCE_PASSWORD
          valueFrom:
            secretKeyRef:
              name: auth-service-secrets
              key: db-password
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8080
          initialDelaySeconds: 30
          periodSeconds: 10
        readinessProbe:
          httpGet:
            path: /actuator/health/readiness
            port: 8080
          initialDelaySeconds: 10
          periodSeconds: 5

---
apiVersion: v1
kind: Service
metadata:
  name: auth-service
  namespace: nursery-system
spec:
  type: ClusterIP
  selector:
    app: auth-service
  ports:
  - port: 8080
    targetPort: 8080

---
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: auth-service-hpa
  namespace: nursery-system
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: auth-service
  minReplicas: 3
  maxReplicas: 10
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 70
```

---

## 10. CI/CD PIPELINE

### 10.1 GitHub Actions Workflow

```yaml
name: Build, Test & Deploy

on:
  push:
    branches: [main, develop]
  pull_request:
    branches: [main, develop]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      
      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      
      - name: Build with Maven
        run: mvn clean package -DskipTests
      
      - name: Run Tests
        run: mvn test
      
      - name: SonarQube Analysis
        env:
          SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
        run: mvn sonar:sonar
      
      - name: Build Docker Image
        run: |
          docker build -t nursery-system/auth-service:${{ github.sha }} .
          docker tag nursery-system/auth-service:${{ github.sha }} \
                     nursery-system/auth-service:latest
      
      - name: Push to ECR
        env:
          AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}
          AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        run: |
          aws ecr get-login-password --region us-east-1 | \
          docker login --username AWS --password-stdin ${{ secrets.ECR_REGISTRY }}
          docker push ${{ secrets.ECR_REGISTRY }}/auth-service:${{ github.sha }}
  
  deploy:
    needs: build
    runs-on: ubuntu-latest
    if: github.ref == 'refs/heads/main'
    steps:
      - uses: actions/checkout@v3
      
      - name: Deploy to ECS
        env:
          AWS_ACCESS_KEY_ID: ${{ secrets.AWS_ACCESS_KEY_ID }}
          AWS_SECRET_ACCESS_KEY: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        run: |
          aws ecs update-service \
            --cluster nursery-prod \
            --service auth-service \
            --force-new-deployment
      
      - name: Notify Slack
        uses: slackapi/slack-github-action@v1
        with:
          webhook-url: ${{ secrets.SLACK_WEBHOOK }}
          payload: |
            {
              "text": "Deployment successful",
              "blocks": [{"type": "section", "text": {"type": "mrkdwn", "text": "Auth Service deployed to production"}}]
            }
```

---

## 11. LOGGING AND MONITORING

### 11.1 Logging Strategy

```
Log Levels:
- DEBUG: Detailed information for debugging
- INFO: General application flow
- WARN: Warning messages, potential issues
- ERROR: Error messages, exceptions

Logging Framework: SLF4J + Logback

Configuration:
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
  </appender>

  <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
    <file>logs/app.log</file>
    <encoder>
      <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
    </encoder>
    <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
      <fileNamePattern>logs/app.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
      <maxFileSize>100MB</maxFileSize>
      <maxHistory>30</maxHistory>
    </rollingPolicy>
  </appender>

  <appender name="ELASTICSEARCH" class="com.internetitem.logback.elasticsearch.ElasticsearchAppender">
    <url>http://elasticsearch:9200</url>
    <index>nursery-logs-%{YYYY.MM.dd}</index>
    <type>_doc</type>
    <includeMdc>true</includeMdc>
  </appender>

  <root level="INFO">
    <appender-ref ref="CONSOLE" />
    <appender-ref ref="FILE" />
    <appender-ref ref="ELASTICSEARCH" />
  </root>
</configuration>
```

### 11.2 Monitoring & Metrics

```
Tool: Prometheus + Grafana

Metrics to Track:
- HTTP request count & latency
- Database query performance
- Cache hit/miss rate
- Message queue lag
- Service availability
- Error rates by service
- Business metrics (orders/hour, revenue/day)

Spring Boot Integration:
- Micrometer for metrics collection
- Prometheus exporter at /actuator/prometheus
```

```yaml
# prometheus.yml
global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'auth-service'
    static_configs:
      - targets: ['localhost:8001']
    metrics_path: '/actuator/prometheus'

  - job_name: 'inventory-service'
    static_configs:
      - targets: ['localhost:8002']
    metrics_path: '/actuator/prometheus'

  # Additional services...

alerting:
  alertmanagers:
    - static_configs:
        - targets: ['localhost:9093']

rule_files:
  - 'alerts.yml'
```

### 11.3 Alerts

```yaml
# alerts.yml
groups:
  - name: application
    rules:
      - alert: HighErrorRate
        expr: rate(http_requests_total{status=~"5.."}[5m]) > 0.05
        for: 5m
        annotations:
          summary: "High error rate detected"

      - alert: DatabaseConnectionPoolExhausted
        expr: hikaricp_connections_active >= 20
        for: 2m
        annotations:
          summary: "Database connection pool running low"

      - alert: ServiceDown
        expr: up{job="auth-service"} == 0
        for: 1m
        annotations:
          summary: "Auth Service is down"
```

---

## 12. SECURITY BEST PRACTICES

### 12.1 Data Security

- **Encryption at Rest:** AES-256 for sensitive data in database
- **Encryption in Transit:** TLS 1.3 for all API communications
- **Password Security:**
  - bcrypt with salt rounds = 12
  - Minimum 12 characters
  - Complexity requirements enforced

### 12.2 API Security

- **SQL Injection Prevention:** Parameterized queries, ORM usage
- **XSS Prevention:** Input sanitization, output encoding
- **CSRF Protection:** CSRF tokens for state-changing operations
- **Rate Limiting:** 100 requests/minute per IP/user
- **CORS:** Strict origin whitelist

### 12.3 Authentication & Authorization

- **JWT Tokens:** HS256 signature, 1-hour expiry
- **Refresh Tokens:** 30-day expiry, stored securely
- **MFA:** TOTP (Time-based One-Time Password) with backup codes
- **Session Management:** Server-side session tracking
- **Token Blacklisting:** Logout invalidates token immediately

### 12.4 Infrastructure Security

- **Secrets Management:** AWS Secrets Manager for credentials
- **Network Security:** VPC with security groups, WAF rules
- **DDoS Protection:** AWS Shield, CloudFlare
- **SSL/TLS Certificates:** Auto-renewal, minimum TLS 1.2
- **IAM Roles:** Least privilege access

### 12.5 Compliance

- **GDPR:** Data retention policies, right to be forgotten
- **PCI-DSS:** For payment processing
- **SOC 2:** Regular audits and compliance checks
- **Data Residency:** Compliance with regional data laws

### 12.6 Security Testing

```
Automated Security Scanning:
- SAST: SonarQube code analysis
- DAST: OWASP ZAP for dynamic testing
- Dependency Scanning: Snyk for vulnerable packages
- Container Scanning: Trivy for image vulnerabilities

Periodic:
- Penetration testing (quarterly)
- Security audit (semi-annually)
- Vulnerability assessment (monthly)
```

---

## 13. SCALABILITY STRATEGY

### 13.1 Horizontal Scaling

- **Stateless Services:** All microservices are stateless
- **Load Balancing:** AWS ALB distributes traffic
- **Database Replication:** Read replicas for query distribution
- **Caching Layer:** Redis for frequently accessed data
- **CDN:** CloudFront for static assets

### 13.2 Vertical Scaling

- **Resource Allocation:** Dynamic resource requests/limits in Kubernetes
- **Instance Types:** Upgrade to larger instances if needed
- **Connection Pooling:** HikariCP for efficient DB connections

### 13.3 Database Scaling

```
Sharding Strategy:
- Shard Key: nursery_id (customer data is sharded by nursery)
- Shards: Initially 4, can expand to 16+
- Cross-shard queries: Aggregation service

Partitioning:
- Time-series data (sensor readings): Partitioned by date
- Logs: Partitioned by month
```

### 13.4 Performance Optimization

- **Query Optimization:** Indexed queries, query analysis
- **Caching Strategy:** Multi-layer caching (application, Redis, CDN)
- **Async Processing:** Kafka for non-blocking operations
- **Batch Processing:** Bulk operations for large datasets
- **API Response Optimization:** Pagination, filtering, projection

---

## 14. DISASTER RECOVERY & BACKUP

### 14.1 Backup Strategy

```
Database Backups:
- Frequency: Hourly snapshots, daily full backups
- Retention: 30 days
- Location: AWS S3 (cross-region)
- Testing: Monthly restore tests

Application Backups:
- Configuration: Version controlled in Git
- Secrets: Encrypted in Secrets Manager

File Storage:
- S3 versioning enabled
- Cross-region replication
- Lifecycle policies (30-day retention)
```

### 14.2 Disaster Recovery Plan

```
RTO (Recovery Time Objective): 4 hours
RPO (Recovery Point Objective): 1 hour

Failure Scenarios:

1. Database Failure:
   ├── Automatic failover to replica (< 1 min)
   ├── Restore from backup if needed (< 30 min)
   └── Verify data integrity

2. Service Crash:
   ├── Kubernetes restarts pod (< 30 sec)
   ├── If persistent, scale up replicas
   └── Manual intervention if needed

3. Data Center Failure:
   ├── Automatic failover to secondary region (< 2 min)
   ├── DNS update to point to secondary
   ├── Verify all services operational
   └── Communicate with customers

4. Security Breach:
   ├── Immediate investigation
   ├── Isolate affected systems
   ├── Notify customers if data exposed
   └── Implement remediation
```

---

## 15. PERFORMANCE BENCHMARKS

### 15.1 Target Metrics

| Metric | Target | Acceptable |
|--------|--------|-----------|
| API Response Time (p95) | < 500ms | < 1s |
| Dashboard Load Time | < 3s | < 5s |
| Page Load (p90) | < 2s | < 4s |
| Database Query (p99) | < 200ms | < 500ms |
| Cache Hit Rate | > 80% | > 70% |
| Throughput | > 1000 req/sec | > 500 req/sec |
| Uptime | 99.9% | > 99% |

### 15.2 Load Testing

```
Tool: Apache JMeter / Gatling

Test Scenarios:
1. Normal Load: 500 concurrent users
2. Peak Load: 2000 concurrent users
3. Stress Test: 5000 concurrent users

Success Criteria:
- Response time maintained
- Error rate < 0.1%
- Database connections not exhausted
- Memory usage stable
```

---

## APPENDICES

### A. API Documentation Tools
- Swagger/OpenAPI for API docs
- Postman collections for manual testing
- Auto-generated API documentation

### B. Development Guidelines
- Code review checklist
- Testing standards
- Documentation requirements
- Deployment procedures

### C. Tools & Libraries

**Backend:**
- Spring Boot 3.x
- Spring Data JPA
- Spring Security
- Springdoc OpenAPI (Swagger)
- Lombok
- MapStruct

**Frontend:**
- React 18, React Router
- Redux Toolkit
- Axios
- React Hook Form
- Jest, React Testing Library

**DevOps:**
- Docker, Kubernetes
- GitHub Actions
- Terraform for IaC
- Ansible for configuration

---

**Document Version Control:**
| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 0.1 | May 2025 | Tech Team | Initial draft |
| 1.0 | May 2025 | Tech Team | Ready for development |

