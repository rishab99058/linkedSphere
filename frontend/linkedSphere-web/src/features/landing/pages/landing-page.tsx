import AboutSection from "../components/about-section";
import CtaSection from "../components/cta-section";
import FeaturesSection from "../components/features-section";
import HeroSection from "../components/hero-section";
import HowItWorksSection from "../components/how-it-works-section";
import LandingFooter from "../components/landing-footer";
import LandingNavbar from "../components/landing-navbar";
import NetworkSection from "../components/network-section";
import ProductPreviewSection from "../components/product-preview-section";
import WhyLinkedSphereSection from "../components/why-linksphere-section";

function LandingPage() {
  return (
    <div className="min-h-screen bg-white">
      <LandingNavbar />
      <main>
        <HeroSection />
        <AboutSection />
        <HowItWorksSection />
        <FeaturesSection />
        <ProductPreviewSection />
        <NetworkSection />
        <WhyLinkedSphereSection />
        <CtaSection />
      </main>
      <LandingFooter />
    </div>
  );
}

export default LandingPage;