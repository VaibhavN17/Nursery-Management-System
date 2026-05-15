# UI/UX DESIGN BRIEF
## Nursery Management System
**Version:** 1.0  
**Date:** May 2025

---

## EXECUTIVE SUMMARY

The Nursery Management System is a **modern, eco-tech platform** designed for small to medium-sized nurseries. The design philosophy emphasizes clarity, efficiency, and an organic aesthetic inspired by nature. The system will feature role-based dashboards, intuitive workflows, and delightful micro-interactions that reflect the green, growth-oriented nature of the business.

**Design Motto:** *"Grow Green, Work Smart"*

---

## 1. DESIGN PHILOSOPHY

### Core Principles

1. **Clarity Over Complexity** - Information architecture should be immediately understandable
2. **Growth-Oriented** - Positive reinforcement, achievement moments, progress tracking
3. **Nature-Inspired** - Organic shapes, natural color palette, breathing space
4. **Efficiency-Driven** - Minimize clicks, reduce cognitive load, optimize workflows
5. **Accessibility First** - WCAG 2.1 AA compliance, keyboard navigation, contrast ratios
6. **Responsive-First** - Mobile-first approach, scales beautifully to desktop
7. **Data Visualization** - Charts that tell stories, metrics that matter
8. **Delightful Details** - Micro-interactions, smooth animations, thoughtful feedback

---

## 2. COLOR PALETTE

### Primary Colors

```
Primary Green (Growth & Nature)
- HEX: #2D6A4F
- RGB: 45, 106, 79
- Usage: Primary actions, headers, navigation
- Psychology: Growth, balance, nature, trust

Secondary Green (Fresh & Energetic)
- HEX: #40916C
- RGB: 64, 145, 108
- Usage: Secondary actions, highlights
- Psychology: Vitality, freshness, harmony

Accent Green (Vibrant & Active)
- HEX: #52B788
- RGB: 82, 183, 136
- Usage: Interactive elements, hover states
- Psychology: Life, energy, optimism
```

### Supporting Colors

```
Neutral (UI Foundation)
- Dark: #1B1B1B (95% opacity)
- Medium: #6B7280 (text on light)
- Light: #F3F4F6 (backgrounds)
- White: #FFFFFF (cards, modals)

Status Colors
- Success: #059669 (green checkmark)
- Warning: #D97706 (amber, caution)
- Error: #DC2626 (red, stop)
- Info: #0284C7 (blue, information)

Data Visualization
- Chart Color 1: #2D6A4F
- Chart Color 2: #40916C
- Chart Color 3: #52B788
- Chart Color 4: #B7E4C7 (light green)
- Chart Color 5: #D8F3DC (very light green)
```

### Dark Mode Palette (Future)

```
Background: #0F172A
Card: #1E293B
Text Primary: #F1F5F9
Text Secondary: #94A3B8
Primary Green (adjusted): #10B981
Accent: #34D399
```

---

## 3. TYPOGRAPHY

### Font Families

```
Display Font: "Poppins" (Google Fonts)
- Weight: 600, 700, 800
- Usage: Page titles, hero sections
- Characteristics: Modern, friendly, geometric
- Fallback: -apple-system, BlinkMacSystemFont

Heading Font: "Inter" (Google Fonts)
- Weight: 600, 700
- Usage: Section titles, card headers
- Characteristics: Clean, highly readable
- Fallback: -apple-system, BlinkMacSystemFont

Body Font: "Inter" (Google Fonts)
- Weight: 400, 500
- Usage: Body text, descriptions
- Characteristics: Excellent screen readability
- Fallback: -apple-system, BlinkMacSystemFont

Monospace: "JetBrains Mono" (code blocks)
- Weight: 400
- Usage: SKU codes, order IDs, code snippets
```

### Font Sizes & Line Heights

```
h1 (Hero Title)
- Desktop: 48px, Weight: 700, Line height: 1.2
- Mobile: 32px, Weight: 700, Line height: 1.3

h2 (Page Title)
- Desktop: 36px, Weight: 700, Line height: 1.2
- Mobile: 24px, Weight: 700, Line height: 1.3

h3 (Section Title)
- Desktop: 24px, Weight: 600, Line height: 1.3
- Mobile: 20px, Weight: 600, Line height: 1.3

h4 (Subsection)
- Desktop: 18px, Weight: 600, Line height: 1.4

Body Large
- 16px, Weight: 400, Line height: 1.6

Body Normal
- 14px, Weight: 400, Line height: 1.5

Body Small
- 12px, Weight: 400, Line height: 1.4

Caption
- 11px, Weight: 500, Line height: 1.4
```

---

## 4. SPACING SYSTEM

### Spacing Scale

```
Consistent 8px Base Unit

xs: 4px     (inner padding, small gaps)
sm: 8px     (button padding, card gaps)
md: 16px    (section margins, spacing)
lg: 24px    (component spacing)
xl: 32px    (section spacing)
2xl: 48px   (major section spacing)
3xl: 64px   (between major sections)
```

### Examples

```
Button padding: 8px (top/bottom) × 16px (left/right)
Card padding: 24px
Section margin bottom: 32px
Sidebar width: 280px
Page max-width: 1440px (with 24px margins)
```

---

## 5. COMPONENT SYSTEM

### 5.1 Buttons

```css
Primary Button
├─ Background: #2D6A4F
├─ Text: white
├─ Padding: 8px 16px
├─ Border radius: 6px
├─ Font weight: 600
├─ Hover: #1F4939 (darker)
├─ Active: #174A33 (pressed)
├─ Disabled: #D1D5DB (gray)
└─ Transition: 150ms ease

Secondary Button
├─ Background: transparent
├─ Border: 2px solid #2D6A4F
├─ Text: #2D6A4F
├─ Padding: 6px 14px (account for border)
├─ Hover: #E8F5E9 (light green bg)
└─ Active: #C8E6C9

Danger Button
├─ Background: #DC2626
├─ Text: white
├─ Hover: #B91C1C
└─ Used for: Delete, cancel operations

Icon Button
├─ Size: 40px × 40px
├─ Padding: 8px
├─ Border radius: 6px
├─ Hover: #F3F4F6
└─ Focus: visible outline
```

### 5.2 Input Fields

```css
Text Input
├─ Border: 1px solid #E5E7EB
├─ Border radius: 6px
├─ Padding: 10px 12px
├─ Font: Inter 14px
├─ Focus: border #2D6A4F, shadow
├─ Placeholder: #9CA3AF (gray)
├─ Error: border #DC2626, red text
├─ Disabled: background #F3F4F6
└─ Height: 40px (mobile comfortable)

Label
├─ Font weight: 600
├─ Font size: 14px
├─ Margin bottom: 8px
├─ Color: #1B1B1B
└─ Required indicator: red *

Checkbox & Radio
├─ Size: 18px × 18px
├─ Checked: #2D6A4F background
├─ Focus: blue outline
├─ Disabled: grayed out
└─ Label positioned right

Dropdown Select
├─ Same styling as text input
├─ Dropdown arrow right-aligned
├─ Option hover: light green
├─ Selected: checkmark
└─ Multi-select: pills with X
```

### 5.3 Cards

```css
Standard Card
├─ Background: white
├─ Border: 1px solid #E5E7EB
├─ Border radius: 8px
├─ Padding: 24px
├─ Box shadow: 0 1px 3px rgba(0,0,0,0.1)
├─ Hover: shadow upgrade
└─ Transition: 200ms ease

Highlighted Card
├─ Border: 1px solid #2D6A4F
├─ Background: #F0F9F7 (very light green)
└─ Used for: Important info, alerts

Interactive Card
├─ Cursor: pointer
├─ Hover: shadow + translate up 2px
└─ Transition: 150ms ease

Card Header
├─ Font size: 18px
├─ Font weight: 600
├─ Margin bottom: 16px
├─ Padding bottom: 16px
└─ Border bottom: 1px solid #E5E7EB
```

### 5.4 Tables

```css
Table Header
├─ Background: #F9FAFB (light gray)
├─ Font weight: 600
├─ Font size: 13px
├─ Text: #6B7280 (medium gray)
├─ Padding: 12px 16px
├─ Border bottom: 1px solid #E5E7EB
└─ Text transform: uppercase (slight)

Table Row
├─ Padding: 16px
├─ Border bottom: 1px solid #E5E7EB
├─ Hover: background #F3F4F6
├─ Alternate rows: normal (no stripe)
└─ Height: 56px (minimum)

Table Cell
├─ Font size: 14px
├─ Font weight: 400
├─ Text align: left (default)
└─ Line height: 1.5

Sortable Column
├─ Header cursor: pointer
├─ Header hover: darker background
├─ Icon: ↑↓ appears on hover
└─ Active sort: ↑ or ↓ visible
```

### 5.5 Modals & Dialogs

```css
Modal Overlay
├─ Background: rgba(0,0,0,0.5)
├─ Z-index: 1000
└─ Click outside: close

Modal Container
├─ Background: white
├─ Border radius: 12px
├─ Max width: 500px (small), 600px (medium)
├─ Padding: 32px
├─ Box shadow: 0 25px 50px rgba(0,0,0,0.15)
└─ Position: center screen

Modal Header
├─ Title: h2 size, bold
├─ Close button: top right
├─ Margin bottom: 24px
└─ Divider: light border

Modal Actions
├─ Position: bottom right
├─ Primary button: right
├─ Secondary button: left
├─ Spacing: 12px between
└─ Margin top: 32px
```

### 5.6 Toast Notifications

```css
Toast Container
├─ Position: top right
├─ Z-index: 1100
├─ Margin: 16px
└─ Max width: 400px

Toast Notification
├─ Background: white
├─ Border left: 4px solid (color by type)
├─ Border radius: 6px
├─ Padding: 16px
├─ Box shadow: 0 10px 25px rgba(0,0,0,0.1)
├─ Animation: slide in from right (200ms)
├─ Auto-dismiss: 4 seconds
└─ Manual close: X button

Success Toast
├─ Border color: #059669
├─ Icon: ✓ green
└─ Color: green text

Error Toast
├─ Border color: #DC2626
├─ Icon: ✗ red
└─ Color: red text

Info Toast
├─ Border color: #0284C7
├─ Icon: ℹ blue
└─ Color: blue text
```

---

## 6. DASHBOARD LAYOUTS

### 6.1 Admin Dashboard

```
┌────────────────────────────────────────────────────────────┐
│ ≡ Logo                     🔍 Search    👤 Profile  ⚙️    │
├────────────────────────────────────────────────────────────┤
│                                                             │
│ SIDEBAR (280px)          MAIN CONTENT                      │
│ ├─ Dashboard              ┌──────────────────────────────┐ │
│ ├─ Nurseries              │ SYSTEM OVERVIEW              │ │
│ ├─ Users                  │                              │ │
│ ├─ Settings               │ Active Nurseries: 150   ▲ 12% │
│ ├─ Reports                │ Total Users: 5,000      ▲ 8%  │
│ ├─ Documentation          │ Monthly Revenue: $50K   ▲ 15% │
│ └─ Help & Support         │ System Uptime: 99.95%       │
│                           └──────────────────────────────┘
│                           
│                           ┌──────────────────────────────┐
│                           │ QUICK ACTIONS                │
│                           │ [Create Nursery]             │
│                           │ [Manage Users]               │
│                           │ [View Alerts]                │
│                           │ [Generate Reports]           │
│                           └──────────────────────────────┘
│                           
│                           ┌──────────────────────────────┐
│                           │ RECENT ACTIVITY              │
│                           │ • User signup: John Nursery  │
│                           │ • Payment received: $5,000   │
│                           │ • System alert: Low disk     │
│                           │ • New order: ORD-2025-001    │
│                           └──────────────────────────────┘
│
└────────────────────────────────────────────────────────────┘
```

### 6.2 Owner Dashboard

```
┌────────────────────────────────────────────────────────────┐
│ Logo    ≡ Menu            🔍 Search    📢 Notifications   │
├────────────────────────────────────────────────────────────┤
│                                                             │
│ SIDEBAR (250px)          MAIN CONTENT                      │
│ ├─ Dashboard              ┌──────────────────────────────┐ │
│ ├─ Orders                 │ TODAY'S PERFORMANCE          │ │
│ ├─ Inventory              │                              │ │
│ ├─ Customers              │ Orders: 45 (↑12%)            │
│ ├─ Deliveries             │ Revenue: ₹35,000 (↑8%)       │
│ ├─ Suppliers              │ Inventory: 5,200 units       │
│ ├─ Greenhouse             │ Delivery Rate: 98%           │
│ ├─ Reports                └──────────────────────────────┘
│ └─ Settings               
│                           ┌──────────────────────────────┐
│                           │ ORDERS CHART                 │
│                           │ (Line graph showing last 30  │
│                           │  days of orders)             │
│                           └──────────────────────────────┘
│                           
│                           ┌──────────────────────────────┐
│                           │ TOP PRODUCTS (This Month)    │
│                           │ 1. Monstera - 120 sold       │
│                           │ 2. Pothos - 95 sold          │
│                           │ 3. Snake Plant - 87 sold     │
│                           └──────────────────────────────┘
│                           
│                           ┌──────────────────────────────┐
│                           │ PENDING ACTIONS              │
│                           │ ⚠️ 5 low stock items         │
│                           │ ⚠️ 2 pending payments        │
│                           │ ⚠️ 3 delayed deliveries      │
│                           └──────────────────────────────┘
│
└────────────────────────────────────────────────────────────┘
```

### 6.3 Inventory Manager Dashboard

```
┌────────────────────────────────────────────────────────────┐
│ Logo    Nursery Selector  🔍 Search    📢 Messages         │
├────────────────────────────────────────────────────────────┤
│                                                             │
│ QUICK FILTERS:  [All] [Low Stock] [Expired] [Overstock]   │
│                                                             │
│ ┌──────────────────────────────────────────────────────────┐│
│ │ INVENTORY STATUS (Location: Main Warehouse)              ││
│ │                                                          ││
│ │ Total Products: 1,245    Last Updated: 2 hours ago      ││
│ │ Total Value: ₹2.5L       Accuracy: 99.2%               ││
│ │                                                          ││
│ │ Table View:                                             ││
│ │ SKU    │ Name        │ Stock │ Reserved │ Available │ .. ││
│ │─────────────────────────────────────────────────────────││
│ │PLT001 │ Monstera   │  450  │   50    │   400    │ ✓  ││
│ │PLT002 │ Pothos     │  320  │   30    │   290    │ ✓  ││
│ │⚠FER01 │ Fertilizer │   15  │   10    │    5     │ 🔴 ││
│ │...                                                      ││
│ └──────────────────────────────────────────────────────────┘│
│                                                             │
│ ┌──────────────────────────────────────────────────────────┐│
│ │ LOW STOCK ALERTS (5 items below minimum)                 ││
│ │                                                          ││
│ │ [Fertilizer] - Current: 5, Min: 20 | [Create PO]       ││
│ │ [Plant Food] - Current: 3, Min: 10 | [Create PO]       ││
│ │ [Pots (10in)] - Current: 8, Min: 15 | [Create PO]      ││
│ │                                                          ││
│ └──────────────────────────────────────────────────────────┘│
│
└────────────────────────────────────────────────────────────┘
```

---

## 7. NAVIGATION DESIGN

### 7.1 Main Navigation (Sidebar)

```
Logo (Nursery name)

├─ Dashboard (house icon)
├─ Orders (shopping bag icon)
├─ Inventory (box icon)
├─ Customers (people icon)
├─ Deliveries (truck icon)
├─ Suppliers (factory icon)
├─ Greenhouse (plant icon)
├─ Reports (chart icon)
├─ Settings (gear icon)
└─ Help & Support (question icon)

Active: Highlighted in green
Hover: Light background
Collapsed mode: Icons only (≡ toggle)
```

### 7.2 Top Navigation (Header)

```
Left:
├─ Hamburger menu (mobile)
└─ Logo (desktop)

Center:
└─ Breadcrumb: Dashboard > Orders > Order #123

Right:
├─ Search bar with recent searches
├─ Notifications bell (with badge count)
├─ User avatar dropdown
│  ├─ Profile
│  ├─ Settings
│  ├─ Help
│  └─ Logout
└─ Settings/Help icon
```

### 7.3 Breadcrumb Navigation

```
Dashboard > Inventory > Products > Monstera

• Clear hierarchy
• Click to navigate back
• Mobile: Hide intermediate levels
• Shows current page context
```

---

## 8. MOBILE UI GUIDELINES

### 8.1 Responsive Breakpoints

```
Mobile (320px - 768px)
- Single column layout
- Full-width cards
- Bottom navigation
- Overlay sidebar on ≡ click
- Touch targets: 48px × 48px

Tablet (768px - 1024px)
- Two column layout
- Sidebar + content
- Adaptive grids
- Touch targets: 44px × 44px

Desktop (1024px+)
- Full layout
- Fixed sidebar
- Flexible grids
- Click targets: 40px
```

### 8.2 Mobile Navigation

```
┌─────────────────────────────┐
│ ≡ Logo    🔍  📢  👤         │ (Header - fixed)
├─────────────────────────────┤
│                             │
│ MAIN CONTENT                │
│ (scrollable)                │
│                             │
│                             │
│                             │
│                             │
│                             │
├─────────────────────────────┤
│  🏠    📦    📊    ⚙️    👤  │ (Bottom tab nav)
│  Home Orders Reports Settings Profile
└─────────────────────────────┘

Bottom navigation:
- 5 main sections
- Icons + labels
- Active: green background
- Swipe navigation supported
```

### 8.3 Mobile Forms

```
Full-width inputs: 100% - 16px padding
Labels above inputs: 14px, bold
Input height: 44px (iOS/Android standard)
Spacing between fields: 16px
Form buttons: Full width
Keyboard: Type-specific (email, tel, number)
```

---

## 9. ACCESSIBILITY GUIDELINES

### 9.1 Color Contrast

```
Text on Background:
- Normal text: 4.5:1 minimum (AA)
- Large text (18pt+): 3:1 minimum (AA)
- Icons: 3:1 minimum

Examples:
✓ #2D6A4F (green) on #FFFFFF (white): 7.2:1
✓ #6B7280 (gray) on #FFFFFF (white): 9.5:1
✗ #8FBC8F (light green) on #FFFFFF: 2.5:1 (FAIL)
```

### 9.2 Keyboard Navigation

```
Tab Order:
1. Header navigation
2. Sidebar links
3. Main content
4. Form fields (logical order)
5. Footer links

Focus Indicators:
- Visible outline: 2px solid #0284C7
- Outline offset: 2px
- Not removed on any element

Keyboard Shortcuts:
- Tab: Next element
- Shift+Tab: Previous element
- Enter: Activate button
- Space: Checkbox/radio
- Esc: Close modals
- Alt+N: New (create)
- Alt+S: Search
```

### 9.3 Screen Reader Support

```
Semantic HTML:
- <button> for clickables (not <div> with onClick)
- <input> with <label>
- <nav>, <main>, <article>
- Heading hierarchy: h1 > h2 > h3

ARIA Labels:
- aria-label for icon buttons
- aria-labelledby for sections
- aria-live for dynamic content
- aria-expanded for accordions
- role attributes where needed

Alt Text:
- Product images: "[Plant name], [size], potted"
- Charts: "Sales trend chart: 30% increase in March"
- Icons: context-dependent or hidden if decorative
```

---

## 10. ANIMATION & MICROINTERACTIONS

### 10.1 Transition Timings

```
Fast: 150ms
- Hover states
- Icon changes
- Tooltip appear
- Easing: ease-out

Normal: 200ms
- Modal slides
- Card transitions
- Page loads
- Easing: ease-in-out

Slow: 300ms
- Page transitions
- Large animations
- Loading states
- Easing: ease-out
```

### 10.2 Specific Interactions

```
Button Hover
- Slight background color change (darkening 10%)
- Shadow increase (if applicable)
- Cursor: pointer
- Duration: 150ms
- Easing: ease-out

Button Click
- Scale down 98% (small press effect)
- Duration: 100ms
- Return to 100% instantly
- Provides tactile feedback

Form Input Focus
- Border color: #2D6A4F
- Box shadow: 0 0 0 3px rgba(45,106,79,0.1)
- Duration: 200ms

Modal Appear
- Overlay fade in: 200ms
- Modal slides up from bottom (mobile) or fades in (desktop)
- Bounce effect (slight): 250ms total

List Item Add
- Fade in from 0 opacity: 200ms
- Slight scale up from 95%
- Stagger: 50ms between items

Loading Spinner
- Continuous rotation: 1s per rotation
- Smooth, linear motion
- Color: primary green
- Size: 24px

Toast Notification
- Slide in from right: 200ms, ease-out
- Auto-dismiss after 4s: fade out 200ms
- Manual close: immediate
```

---

## 11. DATA VISUALIZATION

### 11.1 Chart Design

```
Line Chart (Orders over time)
- Line color: #2D6A4F (primary green)
- Area fill: rgba(45,106,79,0.1) (light transparent)
- Point indicators: circle, 6px diameter
- Grid lines: very light gray (#E5E7EB)
- Hover tooltip: dark background, white text
- Legend: below chart, clickable

Bar Chart (Sales by category)
- Bar color: gradient #40916C to #52B788
- Bar spacing: 8px
- Hover: darkens, shows value
- X-axis: category labels
- Y-axis: value labels, grid lines
- Grouped bars: side-by-side with slight offset

Pie Chart (Inventory by location)
- Colors: green palette (5 shades)
- Slice labels: percent + amount
- Center: total value
- Donut variant: category in center
- Hover: slice highlights, shows details

Status Gauge
- Background: light gray circle
- Filled: green (% complete)
- Center text: number + %
- Color gradient: red (low) → yellow (mid) → green (high)
```

### 11.2 Metrics & Cards

```
KPI Card
┌────────────────────┐
│ Label              │
│ 45 [↑ 12%]        │
│ Secondary: +5 from last week
└────────────────────┘

Metric Icon + Color:
- Green ↑ for increases
- Red ↓ for decreases
- Gray → for no change
- Trending: show sparkline

Progress Bar
┌────────────────────┐
│ Task: 65%          │
│ ████████░░░░░░░░░ │
│ 13/20 items        │
└────────────────────┘
```

---

## 12. DARK MODE GUIDELINES (Future)

```
Activation:
- System preference detection
- Manual toggle in settings
- Remember user preference

Colors:
- Background: #0F172A
- Card: #1E293B
- Text Primary: #F1F5F9
- Text Secondary: #CBD5E1
- Primary Green: #10B981 (brighter for contrast)
- Borders: #334155

Considerations:
- Icons may need adjustment
- Images: darken or add contrast
- Charts: lighter colors for visibility
- Status colors: may need adjustment
```

---

## 13. DESIGN TOKENS (CSS Variables)

```css
:root {
  /* Colors */
  --color-primary: #2D6A4F;
  --color-primary-dark: #1F4939;
  --color-primary-light: #E8F5E9;
  --color-secondary: #40916C;
  --color-accent: #52B788;
  
  /* Neutral */
  --color-dark: #1B1B1B;
  --color-medium: #6B7280;
  --color-light: #F3F4F6;
  --color-white: #FFFFFF;
  
  /* Status */
  --color-success: #059669;
  --color-warning: #D97706;
  --color-error: #DC2626;
  --color-info: #0284C7;
  
  /* Typography */
  --font-display: "Poppins", sans-serif;
  --font-heading: "Inter", sans-serif;
  --font-body: "Inter", sans-serif;
  --font-mono: "JetBrains Mono", monospace;
  
  /* Spacing */
  --spacing-xs: 4px;
  --spacing-sm: 8px;
  --spacing-md: 16px;
  --spacing-lg: 24px;
  --spacing-xl: 32px;
  
  /* Border Radius */
  --radius-sm: 4px;
  --radius-md: 6px;
  --radius-lg: 8px;
  --radius-xl: 12px;
  --radius-full: 9999px;
  
  /* Shadows */
  --shadow-sm: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
  --shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  --shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  --shadow-xl: 0 20px 25px -5px rgba(0, 0, 0, 0.1);
  
  /* Transitions */
  --transition-fast: 150ms ease-out;
  --transition-normal: 200ms ease-in-out;
  --transition-slow: 300ms ease-out;
}
```

---

## 14. WIREFRAME SUGGESTIONS

### 14.1 Homepage/Landing

```
┌────────────────────────────────────┐
│ Header: Logo | Features | Pricing  │
│         Sign Up | Login            │
├────────────────────────────────────┤
│                                    │
│ HERO SECTION                       │
│ "Grow Smarter, Not Harder"         │
│ Tagline + CTA Button               │
│ Hero Image (plant/dashboard)       │
│                                    │
├────────────────────────────────────┤
│ FEATURES SECTION (3 columns)       │
│ ├─ 📦 Inventory Management        │
│ ├─ 📊 Analytics & Reports         │
│ └─ 🚚 Delivery Tracking           │
│                                    │
├────────────────────────────────────┤
│ TESTIMONIALS (carousel)            │
│ "Great system!" - Owner testimonial│
│                                    │
├────────────────────────────────────┤
│ PRICING CARDS                      │
│ Starter | Professional | Enterprise│
│                                    │
├────────────────────────────────────┤
│ CTA SECTION                        │
│ "Ready to grow?" + Sign Up Button  │
│                                    │
├────────────────────────────────────┤
│ Footer: Links | Contact | Social   │
└────────────────────────────────────┘
```

### 14.2 Order Creation Flow Wireframe

```
Step 1: Select Customer
┌──────────────────────────┐
│ Search/Select Customer   │
│ ├─ Existing customer     │
│ └─ New customer form     │
│ [Next] [Cancel]          │
└──────────────────────────┘

Step 2: Add Items
┌──────────────────────────┐
│ Search Products          │
│ [Results with Add btn]   │
│ Shopping Cart:           │
│ ├─ Item 1 - Qty: 2       │
│ ├─ Item 2 - Qty: 1       │
│ └─ Total: ₹              │
│ [Next] [Cancel]          │
└──────────────────────────┘

Step 3: Checkout
┌──────────────────────────┐
│ Delivery Address         │
│ Order Notes              │
│ Apply Discount           │
│ Total: ₹                 │
│ [Create Order] [Cancel]  │
└──────────────────────────┘

Step 4: Confirmation
┌──────────────────────────┐
│ Order #ORD-2025-00123    │
│ Total: ₹2,500            │
│ Status: Confirmed        │
│ [View Order] [Done]      │
└──────────────────────────┘
```

---

## 15. DESIGN SYSTEM TOOLS & RESOURCES

### Tools Used

```
Design & Prototyping: Figma
├─ Component library
├─ Design tokens
├─ Design spec export
└─ Developer handoff

Icon Library: Feather Icons / Heroicons
- 24px & 20px sizes
- Consistent stroke width: 2px
- Outlined style for consistency

Color Tools:
- Contrast checker: webaim.org/contrast
- Palette generator: colorhexa.com
- Color management: Figma tokens plugin

Accessibility:
- WAVE (web accessibility)
- Axe DevTools (browser extension)
- NVDA (screen reader testing)
```

### Design Specifications Export

```
For developers:
- Figma auto-generated CSS/SCSS
- Spacing guidelines
- Component specs with padding/margin
- Color palette with hex codes
- Typography specifications
- Responsive breakpoints
```

---

## 16. DESIGN SYSTEM MAINTENANCE

### Version Control

```
v1.0: Initial system (May 2025)
- Base components
- Color palette
- Typography
- Spacing system

v1.1: Updates based on feedback
- Component refinements
- New micro-interactions
- Performance improvements

v2.0: Major evolution (Q4 2025)
- Dark mode
- Extended components
- Advanced patterns
```

### Governance

```
Design Review Process:
1. Designer proposes new component/change
2. Design system owner reviews
3. Stakeholder feedback
4. Implementation in Figma
5. Developer documentation
6. Rollout announcement
7. Deprecation (if replacing)

Component Lifecycle:
- Proposed → Approved → Implemented → Stable → Deprecated
```

---

## APPENDICES

### A. Design Checklist

When creating new screens/components:

- [ ] Follows spacing scale (4px, 8px, 16px, 24px)
- [ ] Uses defined color palette
- [ ] Typography uses defined sizes
- [ ] Contrast ratio > 4.5:1 for normal text
- [ ] Touch targets > 44px (mobile)
- [ ] Keyboard navigable
- [ ] Responsive breakpoints implemented
- [ ] Error states defined
- [ ] Loading states defined
- [ ] Micro-interactions smooth
- [ ] Screen reader friendly
- [ ] Dark mode compatible
- [ ] Tested on multiple devices
- [ ] Performance optimized
- [ ] Accessible documentation

### B. Figma File Structure

```
Nursery Management System
├─ 📄 Design System
│  ├─ Colors
│  ├─ Typography
│  ├─ Icons
│  ├─ Components
│  │  ├─ Buttons
│  │  ├─ Forms
│  │  ├─ Cards
│  │  ├─ Tables
│  │  ├─ Modals
│  │  └─ Navigation
│  └─ Tokens
│
├─ 📄 Pages
│  ├─ Landing
│  ├─ Authentication
│  ├─ Dashboard
│  ├─ Inventory
│  ├─ Orders
│  ├─ Customers
│  ├─ Delivery
│  ├─ Reports
│  └─ Settings
│
└─ 📄 Prototypes
   ├─ User Flows
   ├─ Interactions
   └─ Animations
```

### C. Handoff Checklist for Developers

```
Before handoff:
- [ ] All components documented
- [ ] Spacing specs accurate
- [ ] Color palette exported
- [ ] Typography specs complete
- [ ] Interactive states shown
- [ ] Responsive variants included
- [ ] Accessibility notes added
- [ ] Animation timing specified
- [ ] Component variants explained
- [ ] Development guidelines documented
```

---

**Document Version Control:**
| Version | Date | Author | Changes |
|---------|------|--------|---------|
| 0.1 | May 2025 | Design Team | Initial draft |
| 1.0 | May 2025 | Design Team | Ready for implementation |

