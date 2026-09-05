import {
  BriefcaseBusiness,
  MessageCircle,
  Network,
  Search,
} from "lucide-react";

const features = [
  {
    icon: Network,
    label: "01",
    title: "Build your network",
    description:
      "Create meaningful professional connections instead of simply collecting contacts.",
  },
  {
    icon: Search,
    label: "02",
    title: "Discover people",
    description:
      "Find professionals, communities, ideas, and conversations that matter to you.",
  },
  {
    icon: MessageCircle,
    label: "03",
    title: "Share your voice",
    description:
      "Share experiences, achievements, insights, and ideas with your sphere.",
  },
  {
    icon: BriefcaseBusiness,
    label: "04",
    title: "Create opportunities",
    description:
      "Turn relationships and conversations into collaborations and professional possibilities.",
  },
];

function FeaturesSection() {
  return (
    <section
      id="features"
      className="bg-white px-5 py-24 sm:px-8 lg:px-10 lg:py-32"
    >
      <div className="mx-auto max-w-7xl">
        <div className="flex flex-col justify-between gap-8 lg:flex-row lg:items-end">
          <div className="max-w-2xl">
            <p className="text-sm font-semibold uppercase tracking-[0.2em] text-blue-600">
              Everything in one sphere
            </p>

            <h2 className="mt-4 text-4xl font-bold tracking-tight text-slate-900 sm:text-5xl">
              More than networking.
              <br />
              <span className="text-blue-600">
                A place to grow.
              </span>
            </h2>
          </div>

          <p className="max-w-md text-base leading-7 text-slate-500">
            Everything you need to build your professional presence and
            participate in a connected community.
          </p>
        </div>

        <div className="mt-16 grid gap-5 sm:grid-cols-2">
          {features.map((feature) => {
            const Icon = feature.icon;

            return (
              <article
                key={feature.label}
                className="group relative overflow-hidden rounded-3xl border border-slate-200 bg-slate-50 p-8 transition-all duration-300 hover:-translate-y-1 hover:bg-white hover:shadow-2xl hover:shadow-slate-200/60"
              >
                <div className="flex items-start justify-between">
                  <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-slate-900 text-white transition-colors group-hover:bg-blue-600">
                    <Icon className="h-5 w-5" />
                  </div>

                  <span className="text-5xl font-bold tracking-tight text-slate-200 transition-colors group-hover:text-blue-100">
                    {feature.label}
                  </span>
                </div>

                <h3 className="mt-12 text-2xl font-bold text-slate-900">
                  {feature.title}
                </h3>

                <p className="mt-3 max-w-lg leading-7 text-slate-500">
                  {feature.description}
                </p>

                <div className="absolute -bottom-16 -right-16 h-40 w-40 rounded-full bg-blue-500/5 blur-2xl transition-opacity group-hover:opacity-100" />
              </article>
            );
          })}
        </div>
      </div>
    </section>
  );
}

export default FeaturesSection;