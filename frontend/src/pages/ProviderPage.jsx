import { useEffect, useMemo, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import { providersEndpoint, inquiryEndpoint } from '../config/api.js';
import { providerCategories, priceOptions } from '../config/constants.js';

const priceLabels = Object.fromEntries(priceOptions.map((option) => [option.value, option.label]));
const categoryLabels = Object.fromEntries(providerCategories.map((category) => [category.value, category.label]));

function ProviderPage() {
  const { slug } = useParams();
  const [provider, setProvider] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [formState, setFormState] = useState({ organizerName: '', email: '', phone: '', message: '', eventDate: '' });
  const [status, setStatus] = useState('idle');

  useEffect(() => {
    let active = true;
    async function loadProvider() {
      setLoading(true);
      try {
        const response = await axios.get(providersEndpoint.bySlug(slug));
        if (!active) return;
        setProvider(response.data);
      } catch (err) {
        if (!active) return;
        setError(err);
      } finally {
        if (active) {
          setLoading(false);
        }
      }
    }
    loadProvider();
    return () => {
      active = false;
    };
  }, [slug]);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setFormState((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (!provider) return;
    setStatus('submitting');
    try {
      await axios.post(inquiryEndpoint, {
        providerId: provider.id,
        ...formState
      });
      setStatus('success');
      setFormState({ organizerName: '', email: '', phone: '', message: '', eventDate: '' });
    } catch (err) {
      setStatus('error');
    }
  };

  const galleryImages = useMemo(() => provider?.images?.slice(0, 6) ?? [], [provider]);

  if (loading) {
    return (
      <div className="mx-auto max-w-4xl px-4 py-16 sm:px-6">
        <p className="text-sm text-neutral-600">Profil wird geladen …</p>
      </div>
    );
  }

  if (error || !provider) {
    return (
      <div className="mx-auto max-w-4xl px-4 py-16 sm:px-6">
        <div className="rounded-3xl border border-red-200 bg-red-50 p-8 text-sm text-red-700">
          Profil konnte nicht geladen werden. Vielleicht wurde es entfernt oder ist noch nicht veröffentlicht.
        </div>
      </div>
    );
  }

  const priceLabel = provider.priceLabel ?? priceLabels[provider.priceTier] ?? provider.priceTier;
  const categoryLabel = categoryLabels[provider.category] ?? provider.category;

  return (
    <div className="mx-auto max-w-5xl px-4 py-16 sm:px-6">
      <div className="flex flex-col gap-12 lg:flex-row">
        <div className="lg:w-2/3">
          <div className="space-y-6">
            <Link to="/search" className="text-sm font-semibold text-neutral-500 hover:text-charcoal">
              ← Zurück zur Suche
            </Link>
            <div>
              <p className="text-xs uppercase tracking-widest text-neutral-500">{categoryLabel}</p>
              <h1 className="mt-2 text-3xl font-semibold text-charcoal md:text-4xl">{provider.name}</h1>
              <p className="mt-4 text-base text-neutral-600">{provider.shortDescription}</p>
            </div>
            {galleryImages.length > 0 && (
              <div className="grid gap-4 sm:grid-cols-2">
                {galleryImages.map((image, index) => (
                  <img key={index} src={image} alt={`${provider.name} Impression ${index + 1}`} className="h-48 w-full rounded-3xl object-cover" />
                ))}
              </div>
            )}
            <dl className="grid gap-6 rounded-3xl border border-sand-200 bg-white/80 p-6 shadow-sm md:grid-cols-2">
              <div>
                <dt className="text-xs font-semibold uppercase tracking-widest text-neutral-500">Ort</dt>
                <dd className="mt-2 text-sm text-neutral-700">
                  {provider.city}
                  {provider.region ? `, ${provider.region}` : ''}
                </dd>
              </div>
              <div>
                <dt className="text-xs font-semibold uppercase tracking-widest text-neutral-500">Preisindikator</dt>
                <dd className="mt-2 text-sm text-neutral-700">{priceLabel}</dd>
              </div>
              {provider.instagramUrl && (
                <div>
                  <dt className="text-xs font-semibold uppercase tracking-widest text-neutral-500">Instagram</dt>
                  <dd className="mt-2 text-sm text-neutral-700">
                    <a href={provider.instagramUrl} target="_blank" rel="noreferrer" className="underline decoration-dotted">
                      {provider.instagramUrl.replace('https://', '')}
                    </a>
                  </dd>
                </div>
              )}
              {provider.videoUrl && (
                <div>
                  <dt className="text-xs font-semibold uppercase tracking-widest text-neutral-500">Video</dt>
                  <dd className="mt-2 text-sm text-neutral-700">
                    <a href={provider.videoUrl} target="_blank" rel="noreferrer" className="underline decoration-dotted">
                      Video ansehen
                    </a>
                  </dd>
                </div>
              )}
            </dl>
          </div>
        </div>
        <aside className="lg:w-1/3">
          <div className="sticky top-28 space-y-6">
            <div className="rounded-3xl border border-sand-200 bg-white/90 p-6 shadow-sm">
              <h2 className="text-xl font-semibold text-charcoal">Direkt anfragen</h2>
              <p className="mt-2 text-sm text-neutral-600">
                Deine Anfrage wird direkt an {provider.name} sowie in Kopie an das WebEvent-Admin-Team gesendet.
              </p>
              <form className="mt-6 space-y-4" onSubmit={handleSubmit}>
                <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
                  Dein Name
                  <input
                    required
                    name="organizerName"
                    value={formState.organizerName}
                    onChange={handleChange}
                    className="rounded-full border-sand-200 px-4 py-2 text-sm focus:border-charcoal focus:ring-charcoal"
                  />
                </label>
                <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
                  E-Mail
                  <input
                    required
                    type="email"
                    name="email"
                    value={formState.email}
                    onChange={handleChange}
                    className="rounded-full border-sand-200 px-4 py-2 text-sm focus:border-charcoal focus:ring-charcoal"
                  />
                </label>
                <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
                  Telefon (optional)
                  <input
                    type="tel"
                    name="phone"
                    value={formState.phone}
                    onChange={handleChange}
                    className="rounded-full border-sand-200 px-4 py-2 text-sm focus:border-charcoal focus:ring-charcoal"
                  />
                </label>
                <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
                  Event-Datum (optional)
                  <input
                    type="date"
                    name="eventDate"
                    value={formState.eventDate}
                    onChange={handleChange}
                    className="rounded-full border-sand-200 px-4 py-2 text-sm focus:border-charcoal focus:ring-charcoal"
                  />
                </label>
                <label className="flex flex-col gap-2 text-sm font-medium text-neutral-700">
                  Nachricht
                  <textarea
                    required
                    name="message"
                    rows="4"
                    value={formState.message}
                    onChange={handleChange}
                    className="rounded-3xl border-sand-200 px-4 py-3 text-sm focus:border-charcoal focus:ring-charcoal"
                  />
                </label>
                <button
                  type="submit"
                  disabled={status === 'submitting'}
                  className="inline-flex w-full items-center justify-center rounded-full bg-charcoal px-4 py-2 text-sm font-semibold text-sand-50 shadow transition hover:bg-charcoal/90 disabled:cursor-not-allowed disabled:opacity-60"
                >
                  {status === 'submitting' ? 'Wird gesendet …' : 'Anfrage senden'}
                </button>
                {status === 'success' && (
                  <p className="text-sm text-emerald-600">Danke! Deine Anfrage wurde gesendet.</p>
                )}
                {status === 'error' && (
                  <p className="text-sm text-red-600">Senden fehlgeschlagen. Bitte versuche es später erneut.</p>
                )}
              </form>
            </div>
            <div className="rounded-3xl border border-sand-200 bg-sand-50/80 p-6 text-sm text-neutral-600">
              <h3 className="text-base font-semibold text-charcoal">Warum WebEvent?</h3>
              <ul className="mt-3 space-y-2">
                <li>✔ Kuratierte Dienstleister für tamilische Hochzeiten</li>
                <li>✔ Direkte Anfrage ohne Provision</li>
                <li>✔ Admin-Team begleitet jeden Schritt</li>
              </ul>
            </div>
          </div>
        </aside>
      </div>
    </div>
  );
}

export default ProviderPage;
