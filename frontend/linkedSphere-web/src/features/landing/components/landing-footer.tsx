import { ArrowUpRight } from "lucide-react";
import { Link } from "react-router-dom";

const LOGO =
  "https://res.cloudinary.com/dws1oujlk/image/upload/v1786865626/ChatGPT_Image_Aug_16_2026_01_03_21_PM_efyyrr.png";

const navigation = [
  { label: "About", href: "#about" },
  { label: "How it works", href: "#how-it-works" },
  { label: "Features", href: "#features" },
  { label: "Why LinkedSphere", href: "#why-linksphere" },
];

function LandingFooter() {
  return (
    <footer className="border-t border-slate-200 bg-white">
      <div className="mx-auto max-w-7xl px-6 lg:px-8">
        {/* Main footer */}
        <div className="grid gap-12 py-14 sm:py-16 lg:grid-cols-[1.4fr_1fr_1fr] lg:gap-20">
          {/* Brand */}
          <div className="max-w-sm">
            <Link to="/" className="inline-flex items-center gap-3">
              <div className="flex h-10 w-10 items-center justify-center rounded-xl bg-slate-950">
                <img
                  src={LOGO}
                  alt="LinkedSphere"
                  className="h-8 w-8 object-contain"
                />
              </div>

              <div>
                <p className="font-bold tracking-tight text-slate-950">
                  LinkedSphere
                </p>

                <p className="text-xs text-slate-500">
                  Connect. Discover. Grow.
                </p>
              </div>
            </Link>

            <p className="mt-6 text-sm leading-6 text-slate-500">
              A professional network built around meaningful connections,
              discovery and opportunities.
            </p>
          </div>

          {/* Explore */}
          <div>
            <p className="text-sm font-semibold text-slate-950">Explore</p>

            <nav className="mt-5 flex flex-col items-start gap-3">
              {navigation.map((item) => (
                <a
                  key={item.href}
                  href={item.href}
                  className="text-sm text-slate-500 transition-colors hover:text-slate-950"
                >
                  {item.label}
                </a>
              ))}
            </nav>
          </div>

          {/* Account */}
          <div>
            <p className="text-sm font-semibold text-slate-950">Account</p>

            <div className="mt-5 flex flex-col items-start gap-3">
              <Link
                to="/auth/login"
                className="group inline-flex items-center gap-1 text-sm text-slate-500 transition-colors hover:text-slate-950"
              >
                Sign in
                <ArrowUpRight
                  size={14}
                  className="transition-transform group-hover:translate-x-0.5 group-hover:-translate-y-0.5"
                />
              </Link>

              <Link
                to="/auth/register"
                className="group inline-flex items-center gap-1 text-sm text-slate-500 transition-colors hover:text-slate-950"
              >
                Create an account
                <ArrowUpRight
                  size={14}
                  className="transition-transform group-hover:translate-x-0.5 group-hover:-translate-y-0.5"
                />
              </Link>
            </div>
          </div>
        </div>

        {/* Bottom bar */}
        <div className="flex flex-col gap-4 border-t border-slate-200 py-6 sm:flex-row sm:items-center sm:justify-between">
          <p className="text-xs text-slate-400">
            © {new Date().getFullYear()} LinkedSphere. All rights reserved.
          </p>

          <p className="text-xs text-slate-400">
            Built for meaningful professional connections.
          </p>
        </div>
      </div>
    </footer>
  );
}

export default LandingFooter;