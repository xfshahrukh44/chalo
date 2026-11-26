# Chalo — Ride Companion

A lightweight, mobile-first ride, fuel and earnings tracker. Its visual approach follows Batwa's lightweight, quick-entry philosophy, using a soft rose and muted-red palette.

## Features
- Ride entry automatically records creation date and time; no date/time prompts.
- Ride distance, fare and petrol price are editable later.
- Daily dashboard: kilometres, estimated fuel (±10% guide), fare, fuel cost and after-fuel earnings.
- Daily closing snapshots with reopen support.
- Weekly and monthly reports with daily breakdown.
- Fuel fill-up log with litres, price per litre and total paid; fill-ups can be edited/deleted.
- Adjustable mileage (defaults to 30 km/L for a 70cc bike).
- CSV export and JSON backup/restore.
- Offline-capable PWA.

## Calculations
- Estimated fuel = ride distance ÷ mileage.
- Estimated ride fuel cost = estimated fuel × petrol price recorded for that ride.
- After-fuel earnings = fare − estimated ride fuel cost.
- Fill-up purchases are recorded separately and are not added again to estimated ride fuel costs, preventing double counting.
- Maintenance and other expenses are excluded.

## Run locally
```bash
python -m http.server 8000
```
Open http://localhost:8000. For PWA installation, use HTTPS or localhost.

## Data
Records are stored in this browser's localStorage, not synced between devices. Use Backup regularly. Previous Chalo v1 ride records are migrated on first load.