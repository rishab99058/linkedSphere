import { Link } from "react-router-dom";

const LOGO =
  "https://res.cloudinary.com/dws1oujlk/image/upload/v1786865626/ChatGPT_Image_Aug_16_2026_01_03_21_PM_efyyrr.png";

function BrandSection() {
  return (
    <Link
      to="/"
      className="mx-auto flex w-fit items-center gap-3 transition-transform duration-300 hover:scale-105"
    >
      <img
        src={LOGO}
        alt="LinkedSphere"
        className="h-14 w-14 object-contain"
      />

      <div>
        <h2 className="text-lg font-bold text-slate-900">
          LinkedSphere
        </h2>

        <p className="text-xs text-slate-500">
          Connect • Discover • Grow
        </p>
      </div>
    </Link>
  );
}

export default BrandSection;