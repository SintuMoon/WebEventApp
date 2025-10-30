import { useMemo } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import ProviderCard from '../components/ProviderCard.jsx';
import SearchFilters from '../components/SearchFilters.jsx';
import { useProviderSearch } from '../hooks/useProviderSearch.js';

function useQueryParams() {
  const location = useLocation();
  return useMemo(() => new URLSearchParams(location.search), [location.search]);
}

function SearchPage() {
  const query = useQueryParams();
  const navigate = useNavigate();
  const defaultFilters = useMemo(
    () => ({
      category: query.get('category') || '',
      city: query.get('city') || ''
    }),
    [query]
  );

  const { filters, updateFilter, resetFilters, result, loading, error } = useProviderSearch(defaultFilters);

  const syncQueryParams = (nextFilters) => {
    const params = new URLSearchParams();
    Object.entries(nextFilters).forEach(([paramKey, paramValue]) => {
      if (paramValue !== '' && paramValue !== null && paramValue !== undefined) {
        params.set(paramKey, paramValue);
      }
    });
    navigate({ pathname: '/search', search: params.toString() }, { replace: true });
  };

  const handleFilterChange = (key, value) => {
    const updated = { ...filters, [key]: value, page: key === 'page' ? value : 0 };
    updateFilter(key, value);
    syncQueryParams(updated);
  };

  const handleReset = () => {
    resetFilters();
    navigate('/search', { replace: true });
  };

  const handlePageChange = (page) => {
    handleFilterChange('page', page);
  };

  return (
    <div className="mx-auto flex max-w-6xl flex-col gap-10 px-4 py-16 sm:px-6">
      <header className="space-y-4">
        <p className="text-sm font-semibold uppercase tracking-widest text-neutral-500">Verzeichnis</p>
        <h1 className="text-3xl font-semibold text-charcoal md:text-4xl">Finde dein Event-Team</h1>
        <p className="max-w-2xl text-base text-neutral-600">
          Nutze Filter für Ort, Budget oder Radius, um die perfekten Dienstleister:innen für deine tamilische Hochzeit oder private Feier zu entdecken.
        </p>
      </header>

      <SearchFilters filters={filters} onChange={handleFilterChange} onReset={handleReset} />

      {loading && <p className="text-sm text-neutral-600">Lade Dienstleister …</p>}
      {error && (
        <div className="rounded-3xl border border-red-200 bg-red-50 p-6 text-sm text-red-700">
          Ups! Beim Laden ist etwas schiefgelaufen. Bitte versuche es später erneut.
        </div>
      )}

      {!loading && !error && (
        <div className="space-y-8">
          <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
            <p className="text-sm text-neutral-600">
              {result.totalElements} Dienstleister:innen gefunden
            </p>
            {result.totalPages > 1 && (
              <div className="flex items-center gap-2 text-sm text-neutral-600">
                <button
                  type="button"
                  onClick={() => handlePageChange(Math.max(result.number - 1, 0))}
                  disabled={result.number === 0}
                  className="rounded-full border border-charcoal px-4 py-1 font-semibold text-charcoal transition hover:bg-charcoal hover:text-sand-50 disabled:cursor-not-allowed disabled:opacity-50"
                >
                  Zurück
                </button>
                <span>
                  Seite {result.number + 1} von {result.totalPages}
                </span>
                <button
                  type="button"
                  onClick={() => handlePageChange(Math.min(result.number + 1, result.totalPages - 1))}
                  disabled={result.number + 1 >= result.totalPages}
                  className="rounded-full border border-charcoal px-4 py-1 font-semibold text-charcoal transition hover:bg-charcoal hover:text-sand-50 disabled:cursor-not-allowed disabled:opacity-50"
                >
                  Weiter
                </button>
              </div>
            )}
          </div>
          {result.content.length === 0 ? (
            <div className="rounded-3xl border border-sand-200 bg-white/80 p-10 text-center text-sm text-neutral-600">
              Noch keine Treffer für diese Filterkombination. Probiere eine andere Kategorie oder entferne den Radius.
            </div>
          ) : (
            <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3">
              {result.content.map((provider) => (
                <ProviderCard
                  key={provider.id}
                  provider={{
                    ...provider,
                    heroImage: provider.images?.[0] ?? provider.heroImage
                  }}
                />
              ))}
            </div>
          )}
        </div>
      )}
    </div>
  );
}

export default SearchPage;
