function AdminPage() {
  return (
    <div className="mx-auto max-w-4xl px-4 py-16 sm:px-6">
      <div className="rounded-3xl border border-sand-200 bg-white/80 p-10 shadow-sm">
        <h1 className="text-3xl font-semibold text-charcoal">Admin Bereich (Preview)</h1>
        <p className="mt-4 text-sm text-neutral-600">
          Das Admin-Backend wird serverseitig in Spring Boot verwaltet. Für das MVP genügt ein Basic-Auth geschütztes Formular, mit dem das interne Team Provider-Profile anlegen oder aktualisieren kann.
        </p>
        <div className="mt-8 space-y-4 text-sm text-neutral-600">
          <p>
            <strong>Quick Access:</strong> Öffne <code className="rounded bg-sand-100 px-2 py-1">/admin</code> im Backend (Basic Auth) und verwende das dortige Formular oder lade eine CSV-Datei.
          </p>
          <p>
            <strong>Empfehlung:</strong> Hinterlege Admin-Zugangsdaten in <code className="rounded bg-sand-100 px-2 py-1">application.yml</code> und teile sie nicht öffentlich.
          </p>
          <p>
            Dieser Platzhalter zeigt, dass im Frontend aktuell kein Admin-UI vorgesehen ist. Für spätere Versionen können hier Statistiken oder Import-Tools ergänzt werden.
          </p>
        </div>
      </div>
    </div>
  );
}

export default AdminPage;
