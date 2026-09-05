import type { ReactNode } from "react";

interface AuthLayoutProps {
  children: ReactNode;
}

function AuthLayout({ children }: AuthLayoutProps) {
  return (
    <main className="min-h-screen bg-slate-50">
      <div className="mx-auto grid min-h-screen max-w-7xl grid-cols-1 lg:grid-cols-2">
        {/* ==================== BRAND / VISUAL SECTION ==================== */}
        <section className="relative hidden overflow-hidden lg:flex">
          {/* Background decoration */}
          <div className="absolute -left-32 -top-32 h-96 w-96 rounded-full bg-blue-100/70 blur-3xl" />

          <div className="absolute -bottom-32 -right-32 h-96 w-96 rounded-full bg-sky-100/70 blur-3xl" />

          <div className="relative flex w-full flex-col justify-center px-12 py-16 xl:px-20">
            {/* Brand */}
            <div className="mb-10">
              <p className="text-2xl font-bold tracking-tight text-slate-900">
                Linked<span className="text-blue-600">Sphere</span>
              </p>

              <div className="mt-2 h-1 w-10 rounded-full bg-blue-600" />
            </div>

            {/* Illustration placeholder */}
            <div className="flex min-h-[320px] items-center justify-center">
              <img
                src="https://res.cloudinary.com/dws1oujlk/image/upload/v1786865626/ChatGPT_Image_Aug_16_2026_01_03_21_PM_efyyrr.png"
                alt="LinkedSphere professional network"
                className="w-full max-w-[420px] object-contain drop-shadow-2xl"
              />
            </div>

            {/* Marketing copy */}
            <div className="mt-8 max-w-lg">
              <h1 className="text-4xl font-bold leading-tight tracking-tight text-slate-900 xl:text-5xl">
                Connect.
                <br />
                Grow.
                <br />
                <span className="text-blue-600">Belong.</span>
              </h1>

              <p className="mt-5 max-w-md text-base leading-7 text-slate-500">
                Build meaningful professional connections, discover
                opportunities, and grow your network with LinkedSphere.
              </p>
            </div>
          </div>
        </section>

        {/* ==================== AUTH SECTION ==================== */}
        <section className="flex items-center justify-center px-5 py-10 sm:px-8 lg:px-12">
          <div className="w-full max-w-md">{children}</div>
        </section>
      </div>
    </main>
  );
}

export default AuthLayout;