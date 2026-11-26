# Chalo — Ride Companion

A mobile-first ride, fuel and earnings tracker with a soft rose and muted-red interface. It follows Batwa's lightweight, quick-entry philosophy.

## Features
- Ride entry automatically records creation date and time; no date/time prompts.
- Ride distance, fare and petrol price can be edited later.
- Daily dashboard: kilometres, estimated fuel, fare, fuel cost and after-fuel earnings.
- Daily closing snapshots with reopen support.
- Weekly and monthly reports with daily breakdown.
- Fuel fill-up log with litres, price per litre and total paid; entries can be edited/deleted.
- Adjustable mileage (defaults to 30 km/L).
- CSV export and JSON backup/restore.
- Offline-capable PWA.

## Calculations
- Estimated fuel = ride distance ÷ mileage.
- Estimated ride fuel cost = estimated fuel × petrol price recorded for that ride.
- After-fuel earnings = fare − estimated ride fuel cost.
- Fuel fill-up purchases are logged separately; they are not added again to estimated ride fuel expenses, avoiding double counting.
- Maintenance and other expenses are not included.

## Run locally
```bash
python -m http.server 8000
```
Open http://localhost:8000. For PWA installation, use HTTPS or localhost.

## Data and privacy
Records are stored in this browser's localStorage and do not automatically sync between devices. Use Backup regularly. Previous Chalo v1 ride records are migrated on first load.
