const LINKEDSPHERE_LOGO =
  "https://res.cloudinary.com/dws1oujlk/image/upload/v1786865626/ChatGPT_Image_Aug_16_2026_01_03_21_PM_efyyrr.png";

function BrandSection() {
  return (
    <div className="flex items-center justify-center">
      <img
        src={LINKEDSPHERE_LOGO}
        alt="LinkedSphere"
        className="h-16 w-16 object-contain"
      />
    </div>
  );
}

export default BrandSection;