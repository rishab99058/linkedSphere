import {
  Compass,
  Network,
  UsersRound,
} from "lucide-react";

const pillars = [
  {
    icon: UsersRound,
    number: "01",
    title: "Connect",
    description:
      "Meet professionals who share your interests, ambitions, and vision.",
  },
  {
    icon: Compass,
    number: "02",
    title: "Discover",
    description:
      "Explore people, ideas, conversations, and opportunities beyond your immediate circle.",
  },
  {
    icon: Network,
    number: "03",
    title: "Grow",
    description:
      "Turn meaningful relationships into collaborations, ideas, and professional growth.",
  },
];

function AboutSection() {
  return (
    <section
      id="about"
      className="bg-white px-5 py-24 sm:px-8 lg:px-10 lg:py-32"
    >
      <div className="mx-auto max-w-7xl">
        <div className="grid gap-16 lg:grid-cols-[0.8fr_1.2fr] lg:items-end">
          <div>
            <p className="text-sm font-semibold uppercase tracking-[0.2em] text-blue-600">
              What is LinkedSphere?
            </p>

            <h2 className="mt-4 text-4xl font-bold tracking-tight text-slate-900 sm:text-5xl">
              One sphere.
              <br />
              <span className="text-blue-600">
                Endless possibilities.
              </span>
            </h2>
          </div>

          <p className="max-w-2xl text-base leading-8 text-slate-500 sm:text-lg">
            LinkedSphere is a professional social network designed around
            meaningful connections. Instead of simply collecting contacts,
            build a sphere where people, ideas, conversations, and
            opportunities come together.
          </p>
        </div>

        <div className="mt-20 grid gap-5 md:grid-cols-3">
          {pillars.map((pillar) => {
            const Icon = pillar.icon;

            return (
              <article
                key={pillar.title}
                className="group rounded-3xl border border-slate-200 bg-slate-50 p-8 transition-all duration-300 hover:-translate-y-1 hover:border-blue-200 hover:bg-white hover:shadow-xl hover:shadow-slate-200/50"
              >
                <div className="flex items-center justify-between">
                  <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-slate-900 text-white">
                    <Icon className="h-5 w-5" />
                  </div>

                  <span className="text-sm font-bold text-slate-300 transition-colors group-hover:text-blue-500">
                    {pillar.number}
                  </span>
                </div>

                <h3 className="mt-8 text-2xl font-bold text-slate-900">
                  {pillar.title}
                </h3>

                <p className="mt-3 leading-7 text-slate-500">
                  {pillar.description}
                </p>
              </article>
            );
          })}
        </div>
      </div>
    </section>
  );
}

export default AboutSection;