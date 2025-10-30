import { Link } from 'react-router-dom';
import { MapPinIcon, CurrencyFrancIcon, StarIcon } from '@heroicons/react/24/outline';
import { providerCategories, priceOptions } from '../config/constants.js';

const priceLabels = Object.fromEntries(priceOptions.map((option) => [option.value, option.label]));
const categoryLabels = Object.fromEntries(providerCategories.map((category) => [category.value, category.label]));

function ProviderCard({ provider }) {
  const {
    slug,
    name,
    city,
    region,
    category,
    priceTier,
    priceLabel,
    shortDescription,
    heroImage,
    featured
  } = provider;

  const displayPrice = priceLabel ?? priceLabels[priceTier] ?? null;

  return (
    <article className="group flex h-full flex-col overflow-hidden rounded-3xl border border-sand-200 bg-white shadow-sm transition hover:-translate-y-1 hover:shadow-lg">
      <div className="relative h-48 w-full overflow-hidden">
        <img
          src={heroImage || '/placeholder.svg'}
          alt={name}
          className="h-full w-full object-cover transition duration-700 group-hover:scale-105"
        />
        {featured && (
          <span className="absolute left-4 top-4 inline-flex items-center gap-1 rounded-full bg-white/90 px-3 py-1 text-xs font-semibold text-charcoal shadow">
            <StarIcon className="h-4 w-4" /> Highlight
          </span>
        )}
      </div>
      <div className="flex flex-1 flex-col gap-4 p-6">
        <div>
          <p className="text-xs uppercase tracking-wide text-neutral-500">{categoryLabels[category] ?? category}</p>
          <h3 className="mt-1 text-xl font-semibold text-charcoal">{name}</h3>
          <p className="mt-2 line-clamp-2 text-sm text-neutral-600">{shortDescription}</p>
        </div>
        <div className="mt-auto flex flex-col gap-2 text-sm text-neutral-600">
          <div className="flex items-center gap-2">
            <MapPinIcon className="h-4 w-4" />
            <span>
              {city}
              {region ? `, ${region}` : ''}
            </span>
          </div>
          {displayPrice && (
            <div className="flex items-center gap-2">
              <CurrencyFrancIcon className="h-4 w-4" />
              <span>{displayPrice}</span>
            </div>
          )}
        </div>
        <Link
          to={`/providers/${slug}`}
          className="inline-flex items-center justify-center rounded-full bg-charcoal px-4 py-2 text-sm font-semibold text-sand-50 transition hover:bg-charcoal/90"
        >
          Profil ansehen
        </Link>
      </div>
    </article>
  );
}

export default ProviderCard;
