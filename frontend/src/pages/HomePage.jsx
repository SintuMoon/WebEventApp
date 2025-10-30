import { Link } from 'react-router-dom';
import { providerCategories } from '../config/constants.js';

const highlights = [
  {
    title: 'Plane wie ein Ambani',
    description: 'Erhalte eine kuratierte Auswahl an spezialisierten Dienstleister:innen für tamilische Hochzeiten und private Feiern in der Schweiz.'
  },
  {
    title: 'Direkte Anfragen ohne Login',
    description: 'Sende eine Anfrage in weniger als einer Minute – wir leiten sie direkt an den Dienstleister und in Kopie an das Admin-Team weiter.'
  },
  {
    title: 'Admin-kuratierte Profile',
    description: 'Alle Profile werden geprüft, damit du nur verlässliche Partner:innen für dein Event erhältst.'
  }
];

const howSteps = [
  {
    title: '1 · Kategorie auswählen',
    description: 'Finde Anbieter nach Kategorie, Ort, Budget oder anhand von Geo-Koordinaten.'
  },
  {
    title: '2 · Profile vergleichen',
    description: 'Sieh dir Bilder, Preisindikatoren, Instagram-Links und Kurzbeschreibungen an.'
  },
  {
    title: '3 · Anfrage senden',
    description: 'Formular ausfüllen, abschicken, fertig. Du erhältst eine Kopie deiner Anfrage.'
  }
];

function HomePage() {
  return (
    <div className="space-y-24 pb-24">
      <section className="gradient-hero border-b border-sand-100">
        <div className="mx-auto flex max-w-6xl flex-col gap-16 px-4 py-24 sm:px-6 lg:flex-row lg:items-center">
          <div className="lg:w-1/2">
            <span className="inline-flex items-center gap-2 rounded-full bg-white/80 px-4 py-1 text-xs font-semibold uppercase tracking-widest text-neutral-500 shadow">
              MVP · Schweiz · Tamilische Hochzeiten
            </span>
            <h1 className="mt-6 text-4xl font-semibold leading-tight text-charcoal md:text-5xl">
              Plane ohne Chaos.<br />
              Finde alle Event-Dienstleister auf einer Plattform.
            </h1>
            <p className="mt-6 text-lg text-neutral-600">
              Wir bündeln Fotograf:innen, DJs, Make-up Artists, Catering, Deko und mehr – damit du dein Event stressfrei organisieren kannst.
            </p>
            <div className="mt-10 flex flex-wrap gap-4">
              <Link
                to="/search"
                className="inline-flex items-center justify-center rounded-full bg-charcoal px-6 py-3 text-sm font-semibold text-sand-50 shadow transition hover:bg-charcoal/90"
              >
                Dienstleister finden
              </Link>
              <Link
                to="/contact"
                className="inline-flex items-center justify-center rounded-full border border-charcoal px-6 py-3 text-sm font-semibold text-charcoal transition hover:bg-charcoal hover:text-sand-50"
              >
                Kontakt aufnehmen
              </Link>
            </div>
          </div>
          <div className="lg:w-1/2">
            <div className="grid gap-4 md:grid-cols-2">
              {highlights.map((item) => (
                <div key={item.title} className="rounded-3xl border border-sand-200 bg-white/80 p-6 shadow-sm backdrop-blur">
                  <h3 className="text-lg font-semibold text-charcoal">{item.title}</h3>
                  <p className="mt-2 text-sm text-neutral-600">{item.description}</p>
                </div>
              ))}
            </div>
          </div>
        </div>
      </section>

      <section>
        <div className="mx-auto flex max-w-6xl flex-col gap-10 px-4 sm:px-6">
          <div className="max-w-2xl">
            <p className="text-sm font-semibold uppercase tracking-widest text-neutral-500">Kategorien</p>
            <h2 className="section-title mt-2">Alle Dienstleister:innen auf einen Blick</h2>
            <p className="section-subtitle mt-4">
              Starte mit tamilischen Hochzeiten – erweitere zu Geburtstagen, Verlobungen oder privaten Feiern.
            </p>
          </div>
          <div className="grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
            {providerCategories.map((category) => (
              <Link
                key={category.value}
                to={`/search?category=${category.value}`}
                className="group flex flex-col justify-between rounded-3xl border border-sand-200 bg-white/80 p-6 shadow-sm transition hover:-translate-y-1 hover:border-charcoal hover:shadow-lg"
              >
                <div>
                  <h3 className="text-lg font-semibold text-charcoal">{category.label}</h3>
                  <p className="mt-2 text-sm text-neutral-600">Top-Anbieter:innen entdecken</p>
                </div>
                <span className="mt-6 inline-flex items-center text-sm font-semibold text-charcoal">
                  Zur Kategorie →
                </span>
              </Link>
            ))}
          </div>
        </div>
      </section>

      <section className="bg-white/80 py-24">
        <div className="mx-auto flex max-w-6xl flex-col gap-12 px-4 sm:px-6 lg:flex-row lg:items-center">
          <div className="lg:w-1/2 space-y-4">
            <p className="text-sm font-semibold uppercase tracking-widest text-neutral-500">So funktioniert es</p>
            <h2 className="section-title">Von der Idee zur Anfrage in drei Schritten</h2>
            <p className="section-subtitle">
              Keine Logins, kein unnötiger Overhead. Du planst, wir kuratieren und leiten weiter.
            </p>
            <div className="grid gap-6">
              {howSteps.map((step) => (
                <div key={step.title} className="rounded-3xl border border-sand-200 bg-sand-50/70 p-6">
                  <h3 className="text-base font-semibold text-charcoal">{step.title}</h3>
                  <p className="mt-2 text-sm text-neutral-600">{step.description}</p>
                </div>
              ))}
            </div>
          </div>
          <div className="lg:w-1/2">
            <div className="overflow-hidden rounded-3xl border border-sand-200 shadow-lg">
              <img
                src="https://images.unsplash.com/photo-1520854221050-0f4caff449fb?auto=format&fit=crop&w=1000&q=80"
                alt="Tamilische Hochzeit"
                className="h-full w-full object-cover"
              />
            </div>
          </div>
        </div>
      </section>

      <section>
        <div className="mx-auto flex max-w-4xl flex-col items-center gap-6 px-4 text-center sm:px-6">
          <h2 className="section-title">Bereit für das nächste Event?</h2>
          <p className="section-subtitle">
            Unser Team arbeitet bereits an Self-Service-Tools, Buchungsmodulen und globalen Erweiterungen.
          </p>
          <Link
            to="/search"
            className="inline-flex items-center justify-center rounded-full bg-charcoal px-8 py-3 text-sm font-semibold text-sand-50 shadow transition hover:bg-charcoal/90"
          >
            Jetzt Dienstleister finden
          </Link>
        </div>
      </section>
    </div>
  );
}

export default HomePage;
