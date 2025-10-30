import { Link, NavLink } from 'react-router-dom';
import { useMemo } from 'react';

const navItems = [
  { to: '/', label: 'Home' },
  { to: '/search', label: 'Dienstleister finden' },
  { to: '/contact', label: 'Kontakt' }
];

function Layout({ children }) {
  const year = useMemo(() => new Date().getFullYear(), []);
  return (
    <div className="flex min-h-screen flex-col bg-sand-50 text-charcoal">
      <header className="sticky top-0 z-50 border-b border-sand-200 bg-sand-50/95 backdrop-blur">
        <div className="mx-auto flex max-w-6xl items-center justify-between px-4 py-4 sm:px-6">
          <Link to="/" className="flex items-center gap-2 text-lg font-semibold">
            <span className="inline-flex h-10 w-10 items-center justify-center rounded-full bg-blush text-charcoal">
              WE
            </span>
            WebEvent
          </Link>
          <nav className="hidden items-center gap-6 text-sm font-medium md:flex">
            {navItems.map((item) => (
              <NavLink
                key={item.to}
                to={item.to}
                className={({ isActive }) =>
                  `transition-colors hover:text-charcoal ${isActive ? 'text-charcoal' : 'text-neutral-600'}`
                }
              >
                {item.label}
              </NavLink>
            ))}
          </nav>
          <Link
            to="/search"
            className="rounded-full bg-charcoal px-5 py-2 text-sm font-semibold text-sand-50 shadow-sm transition hover:bg-charcoal/90"
          >
            Dienstleister finden
          </Link>
        </div>
      </header>
      <main className="flex-1">
        {children}
      </main>
      <footer className="border-t border-sand-200 bg-white/70">
        <div className="mx-auto flex max-w-6xl flex-col gap-4 px-4 py-10 sm:flex-row sm:items-center sm:justify-between sm:px-6">
          <div>
            <p className="text-sm font-semibold">WebEvent</p>
            <p className="text-sm text-neutral-600">Plane Events ohne Chaos – alle Dienstleister an einem Ort.</p>
          </div>
          <div className="flex items-center gap-4 text-sm text-neutral-600">
            <Link to="/contact" className="hover:text-charcoal">
              Impressum & Datenschutz
            </Link>
            <span>© {year}</span>
          </div>
        </div>
      </footer>
    </div>
  );
}

export default Layout;
