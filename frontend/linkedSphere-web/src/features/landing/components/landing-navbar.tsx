import { Menu, X } from "lucide-react";
import { useRef, useState } from "react";
import { Link } from "react-router-dom";

const LINKEDSPHERE_LOGO =
  "https://res.cloudinary.com/dws1oujlk/image/upload/v1786865626/ChatGPT_Image_Aug_16_2026_01_03_21_PM_efyyrr.png";

const NAV_ITEMS = [
  {
    label: "About",
    href: "#about",
  },
  {
    label: "How it works",
    href: "#how-it-works",
  },
  {
    label: "Features",
    href: "#features",
  },
  {
    label: "Why LinkedSphere",
    href: "#why-linksphere",
  },
];

function LandingNavbar() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const headerRef = useRef<HTMLElement>(null);

  const handleSectionNavigation = (
    event: React.MouseEvent<HTMLAnchorElement>,
    href: string,
  ) => {
    event.preventDefault();

    const targetId = href.replace("#", "");
    const section = document.getElementById(targetId);

    if (!section) {
      return;
    }

    /*
     * We don't want the section's badge/title to become the
     * scroll destination.
     *
     * Instead, find the main heading inside the section and
     * position that heading directly below the fixed navbar.
     */
    const heading = section.querySelector("h2");

    if (!heading) {
      return;
    }

    const navbarHeight =
      headerRef.current?.getBoundingClientRect().height ?? 0;

    const gap = 18;

    const headingPosition =
      heading.getBoundingClientRect().top +
      window.scrollY -
      navbarHeight -
      gap;

    window.scrollTo({
      top: Math.max(headingPosition, 0),
      behavior: "smooth",
    });

    window.history.replaceState(null, "", href);

    setMobileMenuOpen(false);
  };

  return (
    <header
      ref={headerRef}
      className="fixed inset-x-0 top-0 z-50 px-4 pt-4"
    >
      <nav className="mx-auto max-w-7xl rounded-2xl border border-white/[0.08] bg-slate-950/70 px-4 py-3 shadow-2xl shadow-black/20 backdrop-blur-xl">
        <div className="flex items-center justify-between">
          {/* Brand */}
          <Link
            to="/"
            className="group flex items-center gap-3"
            aria-label="LinkedSphere home"
          >
            <div className="relative flex h-10 w-10 items-center justify-center">
              <div className="absolute inset-0 rounded-xl bg-blue-500/20 blur-md transition group-hover:bg-blue-400/30" />

              <div className="relative flex h-10 w-10 items-center justify-center rounded-xl border border-white/10 bg-white/[0.05]">
                <img
                  src={LINKEDSPHERE_LOGO}
                  alt="LinkedSphere"
                  className="h-8 w-8 object-contain"
                />
              </div>
            </div>

            <div className="hidden sm:block">
              <p className="text-sm font-semibold tracking-tight text-white">
                LinkedSphere
              </p>

              <p className="text-[10px] text-slate-500">
                Connect. Discover. Grow.
              </p>
            </div>
          </Link>

          {/* Desktop navigation */}
          <div className="hidden items-center gap-8 lg:flex">
            {NAV_ITEMS.map((item) => (
              <a
                key={item.href}
                href={item.href}
                onClick={(event) =>
                  handleSectionNavigation(event, item.href)
                }
                className="text-sm font-medium text-slate-400 transition-colors duration-200 hover:text-white"
              >
                {item.label}
              </a>
            ))}
          </div>

          {/* Desktop actions */}
          <div className="hidden items-center gap-2 lg:flex">
            <Link
              to="/auth/login"
              className="rounded-xl px-4 py-2.5 text-sm font-medium text-slate-300 transition hover:bg-white/[0.05] hover:text-white"
            >
              Sign in
            </Link>

            <Link
              to="/auth/register"
              className="group inline-flex items-center gap-2 rounded-xl border border-blue-400/20 bg-blue-500/10 px-4 py-2.5 text-sm font-semibold text-blue-300 transition hover:border-blue-400/30 hover:bg-blue-500/15 hover:text-blue-200"
            >
              Join the sphere

              <span className="transition-transform duration-200 group-hover:translate-x-0.5">
                →
              </span>
            </Link>
          </div>

          {/* Mobile menu button */}
          <button
            type="button"
            onClick={() =>
              setMobileMenuOpen((previous) => !previous)
            }
            className="rounded-xl border border-white/10 bg-white/[0.04] p-2 text-slate-300 transition hover:bg-white/[0.08] hover:text-white lg:hidden"
            aria-label={
              mobileMenuOpen ? "Close menu" : "Open menu"
            }
            aria-expanded={mobileMenuOpen}
          >
            {mobileMenuOpen ? (
              <X size={20} />
            ) : (
              <Menu size={20} />
            )}
          </button>
        </div>

        {/* Mobile navigation */}
        {mobileMenuOpen && (
          <div className="mt-4 border-t border-white/[0.08] pt-4 lg:hidden">
            <div className="flex flex-col gap-1">
              {NAV_ITEMS.map((item) => (
                <a
                  key={item.href}
                  href={item.href}
                  onClick={(event) =>
                    handleSectionNavigation(event, item.href)
                  }
                  className="rounded-xl px-3 py-3 text-sm font-medium text-slate-300 transition hover:bg-white/[0.05] hover:text-white"
                >
                  {item.label}
                </a>
              ))}

              <div className="mt-3 grid grid-cols-2 gap-2 border-t border-white/[0.08] pt-3">
                <Link
                  to="/auth/login"
                  onClick={() => setMobileMenuOpen(false)}
                  className="rounded-xl border border-white/10 px-4 py-3 text-center text-sm font-medium text-slate-300"
                >
                  Sign in
                </Link>

                <Link
                  to="/auth/register"
                  onClick={() => setMobileMenuOpen(false)}
                  className="rounded-xl bg-blue-500 px-4 py-3 text-center text-sm font-semibold text-white"
                >
                  Join
                </Link>
              </div>
            </div>
          </div>
        )}
      </nav>
    </header>
  );
}

export default LandingNavbar;