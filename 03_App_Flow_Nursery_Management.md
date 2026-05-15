# APP FLOW DOCUMENT
## Nursery Management System
**Version:** 1.0  
**Date:** May 2025

---

## 1. USER JOURNEY OVERVIEW

### 1.1 New Customer Journey (Sign-up & First Order)

```
┌─────────────────────────────────────────────────────────────────┐
│                    LANDING PAGE                                 │
│  - Features overview                                            │
│  - Plant catalog preview                                        │
│  - Call-to-action: "Sign Up" / "Browse Plants"                │
└────────────┬────────────────────────────────────────────────────┘
             │
             ├─► Browse Plants (without login)
             │   └─► View product details
             │       └─► See "Login to purchase" prompt
             │           └─► Redirects to Login
             │
             └─► Click "Sign Up"
                 ▼
         ┌──────────────────────┐
         │   REGISTRATION PAGE  │
         │ Email & Password     │
         │ Name, Phone, Address │
         │ Preferences          │
         └──────────┬───────────┘
                    ▼
         ┌──────────────────────┐
         │   EMAIL VERIFICATION │
         │ Check email inbox    │
         │ Click verification   │
         │ link                 │
         └──────────┬───────────┘
                    ▼
         ┌──────────────────────┐
         │ SETUP COMPLETE       │
         │ Profile created      │
         │ Redirects to catalog │
         └──────────┬───────────┘
                    ▼
         ┌──────────────────────┐
         │ BROWSE & ADD TO CART │
         │ Search plants        │
         │ Filter by category   │
         │ View details         │
         │ Add to cart          │
         └──────────┬───────────┘
                    ▼
         ┌──────────────────────┐
         │    CHECKOUT          │
         │ Review items         │
         │ Delivery address     │
         │ Special instructions │
         │ Promo code           │
         └──────────┬───────────┘
                    ▼
         ┌──────────────────────┐
         │  PAYMENT PAGE        │
         │ Choose payment method│
         │ Card / UPI / Net Bank│
         │ Process payment      │
         └──────────┬───────────┘
                    ▼
         ┌──────────────────────┐
         │ ORDER CONFIRMATION   │
         │ Order number         │
         │ Estimated delivery   │
         │ Email sent           │
         └──────────┬───────────┘
                    ▼
         ┌──────────────────────┐
         │ ORDER TRACKING PAGE  │
         │ Real-time status     │
         │ Delivery location    │
         │ Contact support      │
         └──────────────────────┘
```

### 1.2 Returning Customer Journey (Quick Reorder)

```
┌──────────────────────┐
│   LOGIN PAGE         │
│ Email & password     │
│ "Remember me"        │
│ Forgot password link │
└──────────┬───────────┘
           ▼
   ┌──────────────────┐
   │ CUSTOMER DASHBOARD│
   │ Recent orders     │
   │ Wishlist          │
   │ Loyalty points    │
   │ Recommendations   │
   └──────────┬────────┘
              ▼
      ┌──────────────────┐
      │ QUICK REORDER    │
      │ Select previous  │
      │ items            │
      │ OR               │
      │ Continue browsing│
      └──────────┬───────┘
                 ▼
         ┌──────────────────┐
         │    CHECKOUT      │
         │ Auto-fill address│
         │ Update if needed │
         └──────────┬───────┘
                    ▼
         ┌──────────────────┐
         │    PAYMENT       │
         │ Use saved card   │
         │ Quick payment    │
         └──────────┬───────┘
                    ▼
         ┌──────────────────┐
         │   CONFIRMATION   │
         │ Order placed     │
         │ Tracking active  │
         └──────────────────┘
```

---

## 2. AUTHENTICATION FLOW

### 2.1 Login/Sign-up Flow

```
START
  │
  ├─ Click "Login" ──────────────────────────┐
  │                                          │
  ├─ Click "Sign Up" ───────────┐           │
  │                             │           │
  ▼                             ▼           ▼
┌──────────────┐         ┌────────────┐  ┌──────────────┐
│ LOGIN PAGE   │         │ SIGN UP    │  │ FORGOT PWD   │
│              │         │ FORM       │  │ FORM         │
│ Email input  │         │            │  │              │
│ Password     │         │ Email      │  │ Enter email  │
│ MFA code     │         │ Password   │  │ Send reset   │
│ (if enabled) │         │ Name       │  │ link         │
│ Sign in btn  │         │ Phone      │  │              │
└──────┬───────┘         │ Address    │  └──────┬───────┘
       │                 │ Register   │         │
       ▼                 │ btn        │         ▼
┌──────────────┐         └─────┬──────┘  ┌──────────────┐
│ VERIFY CREDS │               ▼         │ RESET EMAIL  │
│              │         ┌──────────────┐│ SENT         │
│ Valid?       │         │ EMAIL VERIFY ││              │
└──────┬───────┘         │ PAGE         ││ Check email  │
       │                 │              ││ Click link   │
       ├─ No ─────────►  │ Confirm link ││ Open reset   │
       │                 │ button       ││ form         │
       │                 └──────┬───────┘│              │
       │                        ▼        └──────┬───────┘
       │                 ┌──────────────┐       │
       │                 │ VERIFIED     │       ▼
       │                 │              │ ┌──────────────┐
       │                 │ Redirect to  │ │ NEW PASSWORD │
       │                 │ dashboard    │ │ FORM         │
       │                 └──────────────┘ │              │
       │                                   │ Enter pwd    │
       │                                   │ Confirm pwd  │
       │                                   │ Submit btn   │
       │                                   └──────┬───────┘
       │                                          ▼
       ▼                                  ┌──────────────┐
┌──────────────┐                          │ PASSWORD     │
│ MFA REQUIRED?│                          │ RESET SUCCESS│
└──────┬───────┘                          │              │
       │                                  │ Redirect to  │
       ├─ Yes ─────────┐                 │ login        │
       │               │                  └──────────────┘
       │        ┌──────────────┐
       │        │ MFA CODE PAGE│
       │        │              │
       │        │ TOTP code    │
       │        │ OR backup    │
       │        │ code         │
       │        │ Verify btn   │
       │        └──────┬───────┘
       │               ▼
       └──► ┌──────────────┐
            │ AUTHENTICATED│
            │              │
            │ Issue JWT    │
            │ Issue refresh│
            │ token        │
            │              │
            │ Redirect to  │
            │ dashboard    │
            └──────────────┘
```

### 2.2 Multi-Factor Authentication (MFA) Setup

```
USER: Wants to enable MFA

Dashboard
  └─ Settings
      └─ Security
          └─ Enable MFA
              │
              ▼
         ┌──────────────────┐
         │ SETUP MFA PAGE   │
         │                  │
         │ Option 1: TOTP   │
         │ (Google Auth,    │
         │  Authy)          │
         │                  │
         │ Option 2: Backup │
         │ Codes            │
         └──────┬───────────┘
                │
                ├─ Select TOTP
                │   │
                │   ▼
                │ ┌──────────────────┐
                │ │ GENERATE QR CODE │
                │ │                  │
                │ │ Display QR       │
                │ │ Manual code      │
                │ │ Instructions     │
                │ │                  │
                │ │ Scan & Setup     │
                │ │ Instructions     │
                │ └──────┬───────────┘
                │        │
                │        ▼
                │ ┌──────────────────┐
                │ │ VERIFY CODE PAGE │
                │ │                  │
                │ │ Enter TOTP code  │
                │ │ from authenticator
                │ │ Verify button    │
                │ └──────┬───────────┘
                │        │
                └────┬──►│
                     │   ▼
                     │ ┌──────────────────┐
                     │ │ BACKUP CODES     │
                     │ │                  │
                     │ │ Display codes    │
                     │ │ Download option  │
                     │ │ Print option     │
                     │ │ Copy to clipboard│
                     │ │                  │
                     │ │ "Save codes"     │
                     │ │ button           │
                     │ └──────┬───────────┘
                     │        │
                     └───────►│
                              ▼
                     ┌──────────────────┐
                     │ MFA ENABLED      │
                     │                  │
                     │ Setup complete   │
                     │ Requires code    │
                     │ on next login    │
                     │                  │
                     │ Return to settings
                     └──────────────────┘
```

---

## 3. ADMIN FLOW

### 3.1 Admin Dashboard

```
ADMIN LOGIN
  │
  ▼
┌────────────────────────────────┐
│   ADMIN DASHBOARD              │
│                                │
│ ┌──────┬──────┬──────┬──────┐ │
│ │Users │Nursery│Config│Reports
│ │Mgmt  │Mgmt  │     │      │
│ └──────┴──────┴──────┴──────┘ │
│                                │
│ Key Metrics:                   │
│ - Active nurseries: 150        │
│ - Total users: 5000            │
│ - Monthly revenue: $50K        │
│ - System uptime: 99.95%        │
│                                │
│ Quick Actions:                 │
│ - Create new nursery           │
│ - Manage users                 │
│ - View system alerts           │
│ - Generate reports             │
└────────────────────────────────┘
```

### 3.2 User Management Flow

```
Admin Dashboard
  │
  └─ Users Management
      │
      ├─ Search Users
      │   ├─ Search bar
      │   └─ Filter by role, status
      │
      ├─ User List
      │   ├─ Email
      │   ├─ Name
      │   ├─ Role
      │   ├─ Status
      │   ├─ Last login
      │   └─ Actions (Edit, Suspend, Delete)
      │
      ├─ Click Edit User
      │   │
      │   ▼
      │  ┌────────────────────┐
      │  │ EDIT USER PAGE     │
      │  │                    │
      │  │ Name (editable)    │
      │  │ Email (readonly)   │
      │  │ Role dropdown      │
      │  │ Status toggle      │
      │  │ Last login (info)  │
      │  │ MFA status         │
      │  │                    │
      │  │ Update | Cancel    │
      │  └──────────┬─────────┘
      │             │
      │             ▼
      │        User Updated
      │        Audit log created
      │
      └─ Create New User
          │
          ▼
         ┌────────────────────┐
         │ CREATE USER FORM   │
         │                    │
         │ Email (required)   │
         │ Name (required)    │
         │ Role dropdown      │
         │ Nursery selection  │
         │                    │
         │ Create | Cancel    │
         └──────────┬─────────┘
                    │
                    ▼
           Temp password sent
           User receives email
           User sets own password
```

---

## 4. OWNER FLOW

### 4.1 Nursery Owner Dashboard

```
OWNER LOGIN
  │
  ▼
┌─────────────────────────────────────┐
│   OWNER DASHBOARD                   │
│                                     │
│ Welcome, Ramesh!                    │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ Today's Performance              │ │
│ │ ├─ Orders: 45 (↑ 12%)           │ │
│ │ ├─ Revenue: ₹35,000 (↑ 8%)      │ │
│ │ ├─ Inventory: 5,200 units       │ │
│ │ └─ Delivery Rate: 98%           │ │
│ └─────────────────────────────────┘ │
│                                     │
│ ┌─────────────────────────────────┐ │
│ │ Quick Actions                    │ │
│ │ ├─ Create Order                  │ │
│ │ ├─ View Pending Orders           │ │
│ │ ├─ Manage Inventory              │ │
│ │ ├─ Check Deliveries              │ │
│ │ ├─ View Reports                  │ │
│ │ └─ Manage Staff                  │ │
│ └─────────────────────────────────┘ │
│                                     │
│ Sidebar Menu:                       │
│ ├─ Dashboard                        │
│ ├─ Orders                           │
│ ├─ Inventory                        │
│ ├─ Customers                        │
│ ├─ Deliveries                       │
│ ├─ Suppliers                        │
│ ├─ Greenhouse                       │
│ ├─ Reports                          │
│ ├─ Settings                         │
│ └─ Help                             │
└─────────────────────────────────────┘
```

### 4.2 Location & Branch Management

```
Owner Dashboard
  │
  └─ Settings
      │
      └─ Locations Management
          │
          ├─ View All Locations
          │   ├─ Location list with
          │   │  ├─ Name
          │   │  ├─ Address
          │   │  ├─ Staff count
          │   │  ├─ Inventory count
          │   │  └─ Actions (Edit, Delete)
          │   │
          │   └─ Map view of locations
          │
          └─ Add New Location
              │
              ▼
             ┌────────────────────┐
             │ NEW LOCATION FORM  │
             │                    │
             │ Location name      │
             │ Address            │
             │ City               │
             │ State              │
             │ Zip code           │
             │ Phone              │
             │ Manager name       │
             │ Google Maps coords │
             │                    │
             │ Save | Cancel      │
             └──────────┬─────────┘
                        │
                        ▼
                  Location created
                  Can assign inventory
                  Can assign staff
```

---

## 5. INVENTORY FLOW

### 5.1 Stock Management Flow

```
MANAGER/STAFF: Manage Inventory

Dashboard > Inventory
  │
  ├─ View Inventory
  │   │
  │   ▼
  │  ┌────────────────────────────┐
  │  │ INVENTORY LIST PAGE        │
  │  │                            │
  │  │ Filters:                   │
  │  │ - Location dropdown        │
  │  │ - Category dropdown        │
  │  │ - Stock status (Low/OK)    │
  │  │ - Search SKU/Name          │
  │  │                            │
  │  │ Table:                     │
  │  │ - SKU                      │
  │  │ - Name                     │
  │  │ - Category                 │
  │  │ - Stock on hand            │
  │  │ - Reserved                 │
  │  │ - Available                │
  │  │ - Last updated             │
  │  │ - Actions (View, Edit,     │
  │  │   Movement, Delete)        │
  │  └──────────┬─────────────────┘
  │             │
  │             ├─ Click View ──────────┐
  │             │                       │
  │             │              ┌────────────────────┐
  │             │              │ PRODUCT DETAILS    │
  │             │              │                    │
  │             │              │ SKU, Name, Desc    │
  │             │              │ Unit price         │
  │             │              │ Cost price         │
  │             │              │ Supplier info      │
  │             │              │ Stock history      │
  │             │              │ Stock by location  │
  │             │              │                    │
  │             │              │ Close | Edit       │
  │             │              └────────────────────┘
  │             │
  │             └─ Click Edit ──────────┐
  │                                     │
  │                            ┌────────────────────┐
  │                            │ EDIT PRODUCT PAGE  │
  │                            │                    │
  │                            │ Name (editable)    │
  │                            │ Description        │
  │                            │ Unit price         │
  │                            │ Cost price         │
  │                            │ Min stock level    │
  │                            │ Max stock level    │
  │                            │ Supplier           │
  │                            │                    │
  │                            │ Update | Cancel    │
  │                            └──────────┬─────────┘
  │                                       │
  │                                       ▼
  │                              Product updated
  │
  ├─ Add Stock Movement
  │   │
  │   ▼
  │  ┌────────────────────┐
  │  │ STOCK MOVEMENT     │
  │  │ FORM               │
  │  │                    │
  │  │ Product selection  │
  │  │ Movement type:     │
  │  │ - Stock in (from   │
  │  │   supplier)        │
  │  │ - Adjustment       │
  │  │ - Damage/Waste     │
  │  │ - Transfer to      │
  │  │   another location │
  │  │ Quantity           │
  │  │ Reason/Notes       │
  │  │ Batch number       │
  │  │ Expiry date        │
  │  │                    │
  │  │ Submit | Cancel    │
  │  └──────────┬─────────┘
  │             │
  │             ▼
  │      Stock updated
  │      History logged
  │      Alerts triggered
  │      (if low stock)
  │
  └─ Low Stock Alerts
      │
      ▼
     ┌────────────────────┐
     │ LOW STOCK ALERT    │
     │ LIST               │
     │                    │
     │ Products below min │
     │ stock level        │
     │ ├─ SKU             │
     │ ├─ Name            │
     │ ├─ Current stock   │
     │ ├─ Min level       │
     │ └─ Create PO       │
     │                    │
     │ (Auto-suggest      │
     │  purchase order)   │
     └────────────────────┘
```

### 5.2 Bulk Import Flow

```
Manager: Bulk Import Inventory

Inventory Page > Actions > Bulk Import
  │
  ▼
┌──────────────────────────┐
│ BULK IMPORT PAGE         │
│                          │
│ 1. Download template CSV │
│    (SKU, Qty, Location)  │
│                          │
│ 2. Fill spreadsheet      │
│                          │
│ 3. Upload CSV file       │
│    ┌──────────────────┐  │
│    │ Choose file btn  │  │
│    └──────────────────┘  │
└──────────┬───────────────┘
           │
           ▼
    ┌──────────────────┐
    │ FILE SELECTED    │
    │                  │
    │ Filename shown   │
    │ Preview data     │
    │ ├─ Row count     │
    │ ├─ Columns       │
    │ └─ Sample rows   │
    │                  │
    │ Proceed button   │
    └──────────┬───────┘
               │
               ▼
        ┌──────────────────┐
        │ VALIDATION       │
        │                  │
        │ Check for:       │
        │ - Valid SKUs     │
        │ - Qty format     │
        │ - Valid location │
        │ - Duplicates     │
        │                  │
        │ Show errors      │
        │ (if any)         │
        │ Download error   │
        │ report           │
        └──────────┬───────┘
                   │
                   ├─ Errors found
                   │  └─ Fix & reupload
                   │
                   └─ No errors
                      │
                      ▼
              ┌──────────────────┐
              │ CONFIRMATION     │
              │                  │
              │ Summary:         │
              │ - Total products │
              │ - Total qty      │
              │ - Import date    │
              │                  │
              │ Confirm | Cancel │
              └──────────┬───────┘
                         │
                         ▼
               Inventory updated
               Email confirmation
               Import log created
```

---

## 6. ORDER MANAGEMENT FLOW

### 6.1 Create Order (Staff)

```
STAFF: Create New Order

Orders > Create Order
  │
  ▼
┌──────────────────────┐
│ SELECT CUSTOMER      │
│                      │
│ Search customer:     │
│ - By phone number    │
│ - By email           │
│ - By name            │
│ - Existing customer  │
│   selector dropdown  │
│                      │
│ OR                   │
│                      │
│ Create new customer: │
│ - Quick entry form   │
└──────────┬───────────┘
           │
           ▼
    ┌──────────────────┐
    │ ADD ITEMS        │
    │ TO ORDER         │
    │                  │
    │ Search products: │
    │ - By SKU         │
    │ - By name        │
    │ - By category    │
    │                  │
    │ Show available   │
    │ stock            │
    │                  │
    │ Add to cart:     │
    │ - Qty input      │
    │ - Add btn        │
    │                  │
    │ Cart shows:      │
    │ - Product name   │
    │ - Qty            │
    │ - Unit price     │
    │ - Line total     │
    │ - Remove btn     │
    │                  │
    │ Continue adding  │
    │ OR Proceed       │
    └──────────┬───────┘
               │
               ▼
        ┌──────────────────┐
        │ ORDER DETAILS    │
        │                  │
        │ Delivery address │
        │ delivery city    │
        │ zip              │
        │                  │
        │ Order notes      │
        │ Special requests │
        │                  │
        │ Apply discount:  │
        │ - Promo code     │
        │ - Custom discount│
        │                  │
        │ Calculate tax    │
        │ Show total       │
        │                  │
        │ Next | Cancel    │
        └──────────┬───────┘
                   │
                   ▼
            ┌──────────────────┐
            │ PAYMENT DETAILS  │
            │                  │
            │ Choose method:   │
            │ - Credit card    │
            │ - UPI            │
            │ - Net banking    │
            │ - Cash on delivery
            │ - Account credit │
            │                  │
            │ Process payment  │
            │ (if online)      │
            │                  │
            │ Create Order btn │
            └──────────┬───────┘
                       │
                       ▼
              ┌──────────────────┐
              │ CONFIRMATION     │
              │                  │
              │ Order number:    │
              │ #ORD-2025-00123  │
              │                  │
              │ Order total      │
              │ Delivery date    │
              │ Confirmation     │
              │ email sent       │
              │                  │
              │ View | Print     │
              └──────────────────┘
```

### 6.2 Order Tracking (Customer)

```
CUSTOMER: View Order Status

Login > Orders
  │
  ▼
┌──────────────────────────┐
│ MY ORDERS PAGE           │
│                          │
│ Recent Orders:           │
│ ├─ #ORD-2025-00123       │
│ │  ├─ Status: Processing │
│ │  ├─ Total: ₹2,500      │
│ │  ├─ Ordered: 2 days ago│
│ │  └─ Track > Details    │
│ │                        │
│ ├─ #ORD-2025-00122       │
│ │  ├─ Status: Delivered  │
│ │  ├─ Total: ₹1,800      │
│ │  ├─ Delivered: Today   │
│ │  └─ View > Reorder     │
│ │                        │
│ └─ More orders...        │
│                          │
│ Filters:                 │
│ - Status (All, Pending,  │
│   Shipped, Delivered)    │
│ - Date range             │
│                          │
│ Search order number      │
└──────────────────────────┘
     │
     └─ Click Order #ORD-2025-00123
        │
        ▼
       ┌──────────────────────┐
       │ ORDER DETAILS        │
       │                      │
       │ Order Info:          │
       │ - Order #            │
       │ - Ordered date       │
       │ - Total amount       │
       │ - Status: Processing │
       │                      │
       │ Items:               │
       │ - Monstera           │
       │   Qty: 2             │
       │   ₹800 x 2           │
       │ - Potting soil       │
       │   Qty: 1             │
       │   ₹700               │
       │ - Fertilizer         │
       │   Qty: 2             │
       │   ₹250 x 2           │
       │                      │
       │ Delivery Info:       │
       │ ├─ Address:          │
       │ │  45B, Green Lane,  │
       │ │  Mumbai 400001     │
       │ ├─ Status:           │
       │ │  Order confirmed   │
       │ │  (Order received   │
       │ │   by warehouse)    │
       │ ├─ Picking:          │
       │ │  Items being       │
       │ │  picked (2h left)  │
       │ ├─ Packing:          │
       │ │  Waiting           │
       │ ├─ Shipping:         │
       │ │  Waiting           │
       │ ├─ Delivery:         │
       │ │  Waiting           │
       │ │                    │
       │ ├─ Estimated         │
       │ │  delivery:         │
       │ │  May 15, 2025      │
       │ │                    │
       │ └─ Real-time GPS map:│
       │    "Currently        │
       │     processing at    │
       │     warehouse..."    │
       │                      │
       │ Actions:             │
       │ - Cancel order       │
       │ - Modify items       │
       │   (before shipping)  │
       │ - Contact support    │
       │                      │
       │ Back | Share         │
       └──────────────────────┘
```

### 6.3 Real-Time Delivery Tracking

```
CUSTOMER: Track Delivery

Order #ORD-2025-00123
Status: Shipped
  │
  ▼
┌──────────────────────────┐
│ DELIVERY TRACKING PAGE   │
│                          │
│ Order Status Timeline:   │
│                          │
│ ✓ May 13, 10:30 AM      │
│   Order Confirmed       │
│   Warehouse received    │
│                          │
│ ✓ May 13, 3:45 PM       │
│   Items Picked          │
│   From warehouse shelf  │
│                          │
│ ✓ May 13, 5:20 PM       │
│   Packed                │
│   Ready for dispatch    │
│                          │
│ ⟳ May 14, 8:00 AM       │
│   In Transit            │
│   With delivery partner │
│   Agent: Ajay Kumar     │
│                          │
│ ☐ May 15, 2:00 PM       │
│   Out for Delivery      │
│   Estimated time        │
│                          │
│ ☐ May 15, 6:00 PM       │
│   Delivered             │
│   Pending confirmation  │
│                          │
│ ┌────────────────────┐  │
│ │ LIVE MAP           │  │
│ │                    │  │
│ │ ◉ Warehouse        │  │
│ │   ▼                │  │
│ │ Current location   │  │
│ │ (Ajay's phone)     │  │
│ │   ↓                │  │
│ │ ◯ Delivery address │  │
│ │                    │  │
│ │ Est. arrival:      │  │
│ │ 10 mins            │  │
│ └────────────────────┘  │
│                          │
│ Agent Details:           │
│ - Name: Ajay Kumar       │
│ - Phone: +91 98765...    │
│ - Rating: 4.8 stars     │
│ - Vehicles: Bike        │
│                          │
│ Special instructions:    │
│ Call before delivery     │
│                          │
│ Notifications:           │
│ [✓] SMS alerts          │
│ [✓] Push notifications  │
│                          │
│ Actions:                 │
│ - Call agent            │
│ - Chat support          │
│ - Modify delivery       │
│ - Provide feedback      │
└──────────────────────────┘
```

---

## 7. DELIVERY FLOW

### 7.1 Delivery Agent Mobile App Flow

```
DELIVERY AGENT: Morning Routine

App Startup
  │
  ├─ Biometric login
  │  (Face ID / Fingerprint)
  │
  ▼
┌──────────────────────┐
│ TODAY'S DELIVERIES   │
│                      │
│ 15 orders assigned   │
│                      │
│ Route optimized:     │
│ 1. #ORD-001         │
│ 2. #ORD-003         │
│ 3. #ORD-004         │
│ ... (geographically  │
│     ordered)         │
│                      │
│ Start Route > button │
└──────────┬───────────┘
           │
           ▼
    ┌──────────────────┐
    │ PICK UP ORDERS   │
    │ AT WAREHOUSE     │
    │                  │
    │ Orders to pickup:│
    │ 1. #ORD-001      │
    │    2 items       │
    │    Scan QR code  │
    │ 2. #ORD-003      │
    │    3 items       │
    │    Scan QR code  │
    │ ...              │
    │                  │
    │ All picked up?   │
    │ Yes > Start route│
    └──────────┬───────┘
               │
               ▼
        ┌──────────────────┐
        │ NAVIGATE TO      │
        │ FIRST DELIVERY   │
        │                  │
        │ GPS navigation   │
        │ starting...      │
        │                  │
        │ Estimated time:  │
        │ 12 minutes       │
        │                  │
        │ Address preview  │
        │ Customer details │
        │ Items to deliver │
        │ Phone number     │
        │ (1-click call)   │
        │                  │
        │ Start Navigation │
        └──────────┬───────┘
                   │
                   ▼
            (En route to address)
                   │
                   ▼
            Arrive at address
                   │
                   ▼
        ┌──────────────────┐
        │ DELIVERY         │
        │ CONFIRMATION     │
        │                  │
        │ Address shown    │
        │ Call customer    │
        │ (ready button)   │
        │                  │
        │ Customer opens   │
        │ door             │
        │                  │
        │ Verify customer: │
        │ - Name           │
        │ - Phone          │
        │                  │
        │ Show items:      │
        │ - Scan each      │
        │ - Customer       │
        │   confirms       │
        │                  │
        │ Capture POD:     │
        │ - Photo (items)  │
        │ - Signature      │
        │   (customer)     │
        │                  │
        │ Confirm delivery │
        │ button           │
        └──────────┬───────┘
                   │
                   ▼
            Delivery marked
            complete
                   │
                   ▼
        Next order in queue
        (Repeat above)
                   │
                   ▼
        All deliveries
        complete
                   │
                   ▼
        Return to warehouse
        Sync data
        Check-out
```

---

## 8. SUPPLIER MANAGEMENT FLOW

### 8.1 Purchase Order Creation

```
MANAGER: Create Purchase Order

Inventory > Suppliers > Create PO
  │
  ▼
┌──────────────────┐
│ SELECT SUPPLIER  │
│                  │
│ Dropdown or      │
│ Search supplier  │
│ name             │
│                  │
│ Show supplier    │
│ details:         │
│ - Name           │
│ - Contact        │
│ - Location       │
│ - Lead time      │
│ - Min order qty  │
│                  │
│ Select > Next    │
└──────────┬───────┘
           │
           ▼
    ┌──────────────────┐
    │ ADD LINE ITEMS   │
    │                  │
    │ Select products: │
    │ - By category    │
    │ - By supplier    │
    │ - By SKU         │
    │                  │
    │ For each item:   │
    │ - Quantity       │
    │ - Unit price     │
    │ - Delivery date  │
    │                  │
    │ Add to PO cart   │
    │                  │
    │ Cart shows:      │
    │ - Item count     │
    │ - Total cost     │
    │ - Subtotal       │
    │                  │
    │ Continue Adding  │
    │ OR Next          │
    └──────────┬───────┘
               │
               ▼
        ┌──────────────────┐
        │ PO DETAILS       │
        │                  │
        │ PO number: auto  │
        │ Supplier         │
        │ Delivery address │
        │ Expected date    │
        │ Payment terms    │
        │ Special notes    │
        │                  │
        │ Summary:         │
        │ - Items: 5       │
        │ - Subtotal: ₹    │
        │ - Tax: ₹         │
        │ - Total: ₹       │
        │                  │
        │ Create | Cancel  │
        └──────────┬───────┘
                   │
                   ▼
            PO created
            Email sent to
            supplier
            Status: Pending
```

### 8.2 Supplier Dashboard

```
SUPPLIER: Login & View Orders

Supplier Portal > Dashboard
  │
  ▼
┌────────────────────────┐
│ SUPPLIER DASHBOARD     │
│                        │
│ Welcome, John          │
│ (Supplier name)        │
│                        │
│ Quick Stats:           │
│ - Pending Orders: 3    │
│ - Total Value: ₹45K    │
│ - Pending Payment: ₹20K│
│ - Order Fulfillment:   │
│   98%                  │
│                        │
│ Recent Orders:         │
│ ├─ PO-2025-001        │
│ │  Status: Pending    │
│ │  Items: 5           │
│ │  Total: ₹15,000     │
│ │  Action: Accept/    │
│ │  Reject             │
│ │                     │
│ ├─ PO-2025-002        │
│ │  Status: Confirmed  │
│ │  Items: 8           │
│ │  Total: ₹22,000     │
│ │  Action: Ship       │
│ │                     │
│ └─ PO-2025-003        │
│    Status: Shipped    │
│    Items: 3           │
│    Total: ₹8,000      │
│    Delivery: May 15   │
│                       │
│ Action:               │
│ - View all orders     │
│ - View payments       │
│ - Update catalog      │
│ - Message us          │
└────────────────────────┘
```

---

## 9. ERROR HANDLING FLOW

### 9.1 API Error Handling

```
User makes API request
  │
  ▼
┌──────────────────┐
│ REQUEST RECEIVED │
└──────────┬───────┘
           │
           ▼
      Process request
           │
           ├─ 200 OK
           │  ├─ Return success
           │  └─ data
           │
           ├─ 201 Created
           │  └─ Return created
           │     resource
           │
           ├─ 204 No Content
           │  └─ Delete success
           │
           ├─ 400 Bad Request
           │  ├─ Invalid input
           │  ├─ Log error
           │  └─ Return error
           │     message
           │
           ├─ 401 Unauthorized
           │  ├─ Token expired
           │  ├─ Log attempt
           │  └─ Redirect to
           │     login
           │
           ├─ 403 Forbidden
           │  ├─ Permission
           │  │  denied
           │  ├─ Audit log
           │  └─ Return error
           │
           ├─ 404 Not Found
           │  ├─ Resource
           │  │  doesn't exist
           │  └─ Return error
           │
           ├─ 500 Server Error
           │  ├─ Log stack trace
           │  ├─ Notify admin
           │  ├─ Return generic
           │  │  error message
           │  └─ Include request
           │     ID
           │
           └─ 503 Service Down
              ├─ Show maintenance
              │  message
              └─ Retry after time
```

### 9.2 User-Facing Error Handling

```
User Action Fails
  │
  ▼
┌──────────────────────┐
│ ERROR DETECTION      │
│                      │
│ - Validation failed  │
│ - Network error      │
│ - Server error       │
│ - Timeout            │
│ - Permission denied  │
└──────────┬───────────┘
           │
           ├─ Validation Error
           │  (400)
           │  │
           │  ▼
           │ ┌──────────────────┐
           │ │ INLINE ERROR     │
           │ │ MESSAGES         │
           │ │                  │
           │ │ Email field:     │
           │ │ "Invalid format" │
           │ │ (Red highlight)  │
           │ │                  │
           │ │ Password field:  │
           │ │ "Min 8 char"     │
           │ │ (Red highlight)  │
           │ │                  │
           │ │ User can fix     │
           │ │ & retry          │
           │ └──────────────────┘
           │
           ├─ Network Error
           │  │
           │  ▼
           │ ┌──────────────────┐
           │ │ RETRY DIALOG     │
           │ │                  │
           │ │ "Check your      │
           │ │  internet        │
           │ │  connection"     │
           │ │                  │
           │ │ Retry button     │
           │ │ Cancel button    │
           │ │                  │
           │ │ Auto-retry every │
           │ │ 30 seconds       │
           │ └──────────────────┘
           │
           ├─ Server Error (500)
           │  │
           │  ▼
           │ ┌──────────────────┐
           │ │ ERROR TOAST      │
           │ │ (Top right)      │
           │ │                  │
           │ │ ✗ Something      │
           │ │   went wrong     │
           │ │                  │
           │ │ ID: #abc123def   │
           │ │ Time: 2:30 PM    │
           │ │                  │
           │ │ [Support] [Retry]│
           │ │                  │
           │ │ Auto-dismiss in  │
           │ │ 5 seconds        │
           │ └──────────────────┘
           │
           └─ Permission Denied
              (403)
              │
              ▼
             ┌──────────────────┐
             │ ERROR MODAL      │
             │                  │
             │ ⚠ Access Denied  │
             │                  │
             │ You don't have   │
             │ permission to    │
             │ perform this     │
             │ action.          │
             │                  │
             │ Contact admin:   │
             │ admin@...        │
             │                  │
             │ [OK]             │
             └──────────────────┘
```

---

## 10. NOTIFICATION FLOW

### 10.1 Multi-Channel Notifications

```
EVENT: Order Created
  │
  ▼
┌─────────────────────────┐
│ NOTIFICATION SERVICE    │
│                         │
│ Determine recipient     │
│ (Customer, Staff, Admin)│
│                         │
│ Fetch user preferences: │
│ - Email: Yes            │
│ - SMS: Yes              │
│ - In-app: Yes           │
│ - Push: Yes             │
└────────┬────────────────┘
         │
         ├─► EMAIL
         │   │
         │   ├─ Build HTML
         │   │  template
         │   ├─ Add order
         │   │  details
         │   ├─ Add link to
         │   │  track order
         │   └─ Send via AWS
         │      SES
         │
         ├─► SMS
         │   │
         │   ├─ Compose
         │   │  message
         │   │  (160 chars)
         │   ├─ Include order
         │   │  #, total, link
         │   └─ Send via
         │      Twilio
         │
         ├─► IN-APP
         │   │
         │   ├─ Create
         │   │  notification
         │   │  record
         │   ├─ Push to user's
         │   │  notification
         │   │  center
         │   └─ Mark as unread
         │
         └─► PUSH
             │
             ├─ Send to
             │  device tokens
             ├─ iOS (APNs)
             ├─ Android (FCM)
             └─ Add deeplink
                to order page

All notifications logged
& tracked in analytics
```

### 10.2 Notification Preferences

```
User Settings
  │
  └─ Notifications
      │
      ▼
     ┌────────────────────────┐
     │ NOTIFICATION PREFS     │
     │                        │
     │ Email Notifications    │
     │ ├─ Order confirmation │
     │   [✓] Enabled         │
     │ ├─ Order shipped      │
     │   [✓] Enabled         │
     │ ├─ Delivery updates   │
     │   [✓] Enabled         │
     │ ├─ Promo offers       │
     │   [✗] Disabled        │
     │ └─ Weekly summary     │
     │   [✓] Enabled         │
     │                        │
     │ SMS Notifications      │
     │ ├─ Order confirmation │
     │   [✓] Enabled         │
     │ ├─ Delivery arriving  │
     │   [✓] Enabled         │
     │ └─ Offers & deals     │
     │   [✗] Disabled        │
     │                        │
     │ Push Notifications     │
     │ ├─ Order updates      │
     │   [✓] Enabled         │
     │ ├─ Delivery tracking  │
     │   [✓] Enabled         │
     │ └─ Marketing          │
     │   [✗] Disabled        │
     │                        │
     │ In-App Notifications  │
     │ ├─ All notifications  │
     │   [✓] Enabled         │
     │ └─ Quiet hours        │
     │   From 9 PM - 9 AM    │
     │                        │
     │ Save Preferences      │
     └────────────────────────┘
```

---

## 11. DASHBOARD STATE MANAGEMENT

### 11.1 Data Flow & State Updates

```
Backend Service
  │
  ├─ Fetch initial data
  │  (Inventory, Orders, etc.)
  │
  ▼
Frontend Redux Store
  │
  ├─ Store received data
  ├─ Cache in Redux
  ├─ Sync with Redis cache
  │
  ▼
React Component
  │
  ├─ Display data
  ├─ Subscribe to store
  ├─ Re-render on change
  │
  ▼
User Interaction
  │
  ├─ Click, input, etc.
  │
  ▼
Dispatch Action
  │
  ├─ thunks/actions
  ├─ Call API endpoint
  │
  ▼
API Call
  │
  ├─ Send request
  ├─ Handle response
  ├─ Update store
  ├─ Trigger re-render
  │
  ▼
Components Updated
```

---

## 12. SESSION & TIMEOUT FLOW

### 12.1 Session Management

```
User Logs In
  │
  ├─ Credentials verified
  ├─ JWT token issued (1 hour)
  ├─ Refresh token issued (30 days)
  ├─ Session created in backend
  │
  ▼
User Active
  │
  ├─ Making API calls
  ├─ Session extends on each call
  │
  ▼
User Goes Idle (15 mins)
  │
  ├─ Show warning dialog
  │  "Session will expire
  │   in 5 minutes"
  │
  ├─ [Dismiss] [Stay Logged In]
  │
  ├─ If no action:
  │  Logout after 5 mins
  │
  └─ If "Stay Logged In":
     Refresh JWT token
     Extend session
     Continue
```

---

## APPENDICES

### A. Status Codes Reference

| Context | Codes |
|---------|-------|
| Order Status | pending, processing, ready, shipped, delivered, cancelled |
| Payment Status | unpaid, paid, refunded, failed |
| Delivery Status | pending, assigned, picked, in_transit, delivered, failed |
| User Status | active, inactive, suspended, deleted |

### B. Navigation Shortcuts

- Cmd/Ctrl + K: Search
- Cmd/Ctrl + /: Help
- Esc: Close modals/dialogs
- Tab: Navigate fields
- Enter: Submit form

### C. Keyboard Shortcuts (Future)

- Cmd + O: Create order
- Cmd + I: Inventory
- Cmd + D: Dashboard
- Cmd + S: Settings

---

**Document Version Control:**
| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 0.1 | May 2025 | Product Team | Initial draft |
| 1.0 | May 2025 | Product Team | Ready for development |

