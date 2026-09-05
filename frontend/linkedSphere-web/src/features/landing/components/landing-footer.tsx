import { Link } from "react-router-dom";

const LINKEDSPHERE_LOGO =
  "https://res.cloudinary.com/dws1oujlk/image/upload/v1786865626/ChatGPT_Image_Aug_16_2026_01_03_21_PM_efyyrr.png";

function LandingFooter() {
  return (
    <footer className="border-t border-slate-200 bg-white">
      <div className="mx-auto max-w-7xl px-5 py-10 sm:px-8 lg:px-10">
        <div className="flex flex-col gap-8 md:flex-row md:items-center md:justify-between">
          {/* Brand */}
          <Link
            to="/"
            className="flex items-center gap-3"
          >
            <img
              src={LINKEDSPHERE_LOGO}
              alt="LinkedSphere"
              className="h-10 w-10 object-contain"
            />

            <span className="text-lg font-bold tracking-tight text-slate-900">
              Linked<span className="text-blue-600">Sphere</span>
            </span>
          </Link>

          {/* Links */}
          <div className="flex flex-wrap items-center gap-6 text-sm">
            <a
              href="#about"
              className="text-slate-500 hover:text-slate-900"
            >
              About
            </a>

            <a
              href="#features"
              className="text-slate-500 hover:text-slate-900"
            >
              Features
            </a>

            <a
              href="#why"
              className="text-slate-500 hover:text-slate-900"
            >
              Why LinkedSphere
            </a>

            <Link
              to="/auth/login"
              className="font-semibold text-slate-700 hover:text-blue-600"
            >
              Sign in
            </Link>
          </div>
        </div>

        <div className="mt-8 border-t border-slate-100 pt-6">
          <p className="text-sm text-slate-400">
            Connect. Discover. Grow.
          </p>

          <p className="mt-2 text-xs text-slate-400">
            © {new Date().getFullYear()} LinkedSphere. All rights reserved.
          </p>
        </div>
      </div>
    </footer>
  );
}

export default LandingFooter;