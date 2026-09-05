import { Menu, X } from "lucide-react";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const LINKEDSPHERE_LOGO =
  "https://res.cloudinary.com/dws1oujlk/image/upload/v1786865626/ChatGPT_Image_Aug_16_2026_01_03_21_PM_efyyrr.png";

const navItems = [
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
    href: "#why",
  },
];

function LandingNavbar() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isScrolled, setIsScrolled] = useState(false);

  useEffect(() => {
    function handleScroll() {
      setIsScrolled(window.scrollY > 20);
    }

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  function closeMenu() {
    setIsMenuOpen(false);
  }

  return (
    <header className="fixed left-0 right-0 top-0 z-50 px-4 pt-4 sm:px-6">
      <nav
        className={`mx-auto max-w-7xl rounded-2xl border transition-all duration-300 ${
          isScrolled
            ? "border-slate-200/70 bg-white/90 shadow-lg shadow-slate-900/5 backdrop-blur-xl"
            : "border-white/10 bg-slate-950/70 backdrop-blur-xl"
        }`}
      >
        <div className="flex h-16 items-center justify-between px-4 sm:px-6">
          {/* Brand */}
          <Link
            to="/"
            onClick={closeMenu}
            className="flex items-center gap-2.5"
          >
            <img
              src={LINKEDSPHERE_LOGO}
              alt="LinkedSphere"
              className="h-10 w-10 object-contain"
            />

            <span
              className={`text-lg font-bold tracking-tight transition-colors sm:text-xl ${
                isScrolled ? "text-slate-900" : "text-white"
              }`}
            >
              Linked<span className="text-blue-500">Sphere</span>
            </span>
          </Link>

          {/* Desktop links */}
          <div className="hidden items-center gap-7 lg:flex">
            {navItems.map((item) => (
              <a
                key={item.href}
                href={item.href}
                className={`text-sm font-medium transition-colors ${
                  isScrolled
                    ? "text-slate-600 hover:text-slate-950"
                    : "text-slate-300 hover:text-white"
                }`}
              >
                {item.label}
              </a>
            ))}
          </div>

          {/* Desktop actions */}
          <div className="hidden items-center gap-2 md:flex">
            <Link
              to="/auth/login"
              className={`rounded-xl px-4 py-2.5 text-sm font-semibold transition-colors ${
                isScrolled
                  ? "text-slate-700 hover:bg-slate-100"
                  : "text-white hover:bg-white/10"
              }`}
            >
              Sign in
            </Link>

            <Link
              to="/auth/register"
              className="rounded-xl bg-blue-600 px-4 py-2.5 text-sm font-semibold text-white shadow-lg shadow-blue-600/20 transition-all hover:bg-blue-500 hover:shadow-blue-600/30"
            >
              Join LinkedSphere
            </Link>
          </div>

          {/* Mobile button */}
          <button
            type="button"
            onClick={() => setIsMenuOpen((previous) => !previous)}
            className={`flex h-10 w-10 items-center justify-center rounded-xl transition-colors md:hidden ${
              isScrolled
                ? "text-slate-700 hover:bg-slate-100"
                : "text-white hover:bg-white/10"
            }`}
            aria-label={isMenuOpen ? "Close menu" : "Open menu"}
            aria-expanded={isMenuOpen}
          >
            {isMenuOpen ? (
              <X className="h-5 w-5" />
            ) : (
              <Menu className="h-5 w-5" />
            )}
          </button>
        </div>

        {/* Mobile menu */}
        {isMenuOpen && (
          <div
            className={`border-t px-4 py-4 md:hidden ${
              isScrolled
                ? "border-slate-200"
                : "border-white/10"
            }`}
          >
            <div className="flex flex-col gap-1">
              {navItems.map((item) => (
                <a
                  key={item.href}
                  href={item.href}
                  onClick={closeMenu}
                  className={`rounded-xl px-3 py-3 text-sm font-medium ${
                    isScrolled
                      ? "text-slate-700 hover:bg-slate-100"
                      : "text-slate-200 hover:bg-white/10"
                  }`}
                >
                  {item.label}
                </a>
              ))}

              <div
                className={`my-2 border-t ${
                  isScrolled
                    ? "border-slate-200"
                    : "border-white/10"
                }`}
              />

              <Link
                to="/auth/login"
                onClick={closeMenu}
                className={`rounded-xl px-3 py-3 text-sm font-semibold ${
                  isScrolled
                    ? "text-slate-700 hover:bg-slate-100"
                    : "text-white hover:bg-white/10"
                }`}
              >
                Sign in
              </Link>

              <Link
                to="/auth/register"
                onClick={closeMenu}
                className="rounded-xl bg-blue-600 px-3 py-3 text-center text-sm font-semibold text-white"
              >
                Join LinkedSphere
              </Link>
            </div>
          </div>
        )}
      </nav>
    </header>
  );
}

export default LandingNavbar;