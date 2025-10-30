function ContactPage() {
  return (
    <div className="mx-auto max-w-3xl px-4 py-16 sm:px-6">
      <div className="rounded-3xl border border-sand-200 bg-white/80 p-10 shadow-sm">
        <h1 className="text-3xl font-semibold text-charcoal">Kontakt & Impressum</h1>
        <p className="mt-4 text-sm text-neutral-600">
          WebEvent ist eine kuratierte Plattform für Event-Dienstleister:innen in der Schweiz. Wir vermitteln Kontaktanfragen zwischen Veranstalter:innen und Dienstleister:innen. Verträge und Zahlungen werden direkt zwischen den Parteien geregelt.
        </p>

        <div className="mt-8 grid gap-6 md:grid-cols-2">
          <div>
            <h2 className="text-sm font-semibold uppercase tracking-widest text-neutral-500">Kontakt</h2>
            <p className="mt-2 text-sm text-neutral-700">
              E-Mail: <a href="mailto:hello@webevent.ch" className="underline decoration-dotted">hello@webevent.ch</a>
            </p>
            <p className="mt-1 text-sm text-neutral-700">Telefon: +41 79 000 00 00</p>
          </div>
          <div>
            <h2 className="text-sm font-semibold uppercase tracking-widest text-neutral-500">Adresse</h2>
            <p className="mt-2 text-sm text-neutral-700">WebEvent GmbH</p>
            <p className="text-sm text-neutral-700">Bahnhofstrasse 10</p>
            <p className="text-sm text-neutral-700">8001 Zürich</p>
          </div>
        </div>

        <div className="mt-8 space-y-4 text-sm text-neutral-600">
          <p>
            Datenschutz: Wir speichern Kontaktanfragen und leiten sie an den gewählten Dienstleister weiter. Eine Kopie wird intern für Supportzwecke gespeichert. Alle Daten werden auf Servern in der EU/Schweiz verarbeitet.
          </p>
          <p>AGB: Die Plattform agiert als Vermittler. Für Verträge, Leistungen und Zahlungen sind die Parteien verantwortlich.</p>
          <p>Bildrechte: Alle verwendeten Bilder sind freigegeben oder durch die Dienstleister:innen bereitgestellt.</p>
        </div>
      </div>
    </div>
  );
}

export default ContactPage;
