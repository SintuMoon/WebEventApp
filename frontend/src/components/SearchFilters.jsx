import { providerCategories, priceOptions } from '../config/constants.js';

function SearchFilters({ filters, onChange, onReset }) {
  return (
    <form className="grid gap-4 rounded-3xl border border-sand-200 bg-white/80 p-6 shadow-sm backdrop-blur">
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
          Kategorie
          <select
            value={filters.category}
            onChange={(event) => onChange('category', event.target.value)}
            className="rounded-full border-sand-200 bg-white px-4 py-2 text-sm focus:border-charcoal focus:ring-charcoal"
          >
            <option value="">Alle Kategorien</option>
            {providerCategories.map((category) => (
              <option key={category.value} value={category.value}>
                {category.label}
              </option>
            ))}
          </select>
        </label>
        <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
          Ort / Stadt
          <input
            type="text"
            value={filters.city}
            onChange={(event) => onChange('city', event.target.value)}
            placeholder="z. B. Zürich"
            className="rounded-full border-sand-200 px-4 py-2 text-sm focus:border-charcoal focus:ring-charcoal"
          />
        </label>
        <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
          Region / Kanton
          <input
            type="text"
            value={filters.region}
            onChange={(event) => onChange('region', event.target.value)}
            placeholder="z. B. ZH"
            className="rounded-full border-sand-200 px-4 py-2 text-sm focus:border-charcoal focus:ring-charcoal"
          />
        </label>
        <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
          Preis
          <select
            value={filters.priceTier}
            onChange={(event) => onChange('priceTier', event.target.value)}
            className="rounded-full border-sand-200 bg-white px-4 py-2 text-sm focus:border-charcoal focus:ring-charcoal"
          >
            <option value="">Alle Preisbereiche</option>
            {priceOptions.map((option) => (
              <option key={option.value} value={option.value}>
                {option.label}
              </option>
            ))}
          </select>
        </label>
      </div>
      <div className="grid gap-4 md:grid-cols-3">
        <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
          Breitengrad
          <input
            type="number"
            step="0.0001"
            value={filters.latitude}
            onChange={(event) => onChange('latitude', event.target.value)}
            placeholder="47.3769"
            className="rounded-full border-sand-200 px-4 py-2 text-sm focus:border-charcoal focus:ring-charcoal"
          />
        </label>
        <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
          Längengrad
          <input
            type="number"
            step="0.0001"
            value={filters.longitude}
            onChange={(event) => onChange('longitude', event.target.value)}
            placeholder="8.5417"
            className="rounded-full border-sand-200 px-4 py-2 text-sm focus:border-charcoal focus:ring-charcoal"
          />
        </label>
        <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
          Radius (km)
          <input
            type="number"
            min="1"
            max="500"
            value={filters.radiusKm}
            onChange={(event) => onChange('radiusKm', event.target.value)}
            placeholder="50"
            className="rounded-full border-sand-200 px-4 py-2 text-sm focus:border-charcoal focus:ring-charcoal"
          />
        </label>
      </div>
      <div className="flex items-center justify-between">
        <p className="text-sm text-neutral-600">
          Tipp: Für tamilische Hochzeiten findest du Top-Expert:innen unter "Fotografie", "Dekoration" und "Catering".
        </p>
        <button
          type="button"
          onClick={onReset}
          className="rounded-full border border-charcoal px-4 py-2 text-sm font-semibold text-charcoal transition hover:bg-charcoal hover:text-sand-50"
        >
          Filter zurücksetzen
        </button>
      </div>
    </form>
  );
}

export default SearchFilters;
