import { Link } from "react-router-dom";

const LINKEDSPHERE_LOGO =
  "https://res.cloudinary.com/dws1oujlk/image/upload/v1786865626/ChatGPT_Image_Aug_16_2026_01_03_21_PM_efyyrr.png";

function AuthMobileBrand() {
  return (
    <Link
      to="/"
      aria-label="Go to LinkedSphere home"
      className="mb-7 flex items-center justify-center gap-3 rounded-2xl p-2 transition duration-200 hover:bg-white/[0.04] focus:outline-none focus:ring-2 focus:ring-blue-400/50 lg:hidden"
    >
      <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-white/5 ring-1 ring-white/10">
        <img
          src={LINKEDSPHERE_LOGO}
          alt="LinkedSphere"
          className="h-9 w-9 object-contain"
        />
      </div>

      <div className="text-left">
        <p className="font-bold text-white">
          LinkedSphere
        </p>

        <p className="text-xs text-slate-400">
          Connect. Discover. Grow.
        </p>
      </div>
    </Link>
  );
}

export default AuthMobileBrand;