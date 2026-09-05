import {
  ArrowRight,
  CircleUserRound,
  Link2,
  Rocket,
} from "lucide-react";

const steps = [
  {
    icon: CircleUserRound,
    number: "01",
    title: "Create your identity",
    description:
      "Build your professional presence and tell your sphere who you are.",
  },
  {
    icon: Link2,
    number: "02",
    title: "Make meaningful connections",
    description:
      "Discover and connect with people who align with your interests and goals.",
  },
  {
    icon: Rocket,
    number: "03",
    title: "Grow together",
    description:
      "Share ideas, start conversations, collaborate, and create new opportunities.",
  },
];

function HowItWorksSection() {
  return (
    <section
      id="how-it-works"
      className="bg-slate-50 px-5 py-24 sm:px-8 lg:px-10 lg:py-32"
    >
      <div className="mx-auto max-w-7xl">
        <div className="mx-auto max-w-3xl text-center">
          <p className="text-sm font-semibold uppercase tracking-[0.2em] text-blue-600">
            How it works
          </p>

          <h2 className="mt-4 text-4xl font-bold tracking-tight text-slate-900 sm:text-5xl">
            Your sphere starts
            <br />
            with <span className="text-blue-600">one connection.</span>
          </h2>

          <p className="mt-6 text-lg leading-8 text-slate-500">
            Getting started is simple. Create your presence, find your people,
            and start building something meaningful.
          </p>
        </div>

        <div className="relative mt-20 grid gap-8 md:grid-cols-3">
          {/* Connector line */}
          <div className="absolute left-[16%] right-[16%] top-12 hidden border-t border-dashed border-slate-300 md:block" />

          {steps.map((step) => {
            const Icon = step.icon;

            return (
              <article
                key={step.number}
                className="relative z-10 text-center"
              >
                <div className="mx-auto flex h-24 w-24 items-center justify-center rounded-full border-8 border-slate-50 bg-white shadow-lg shadow-slate-200/70">
                  <Icon className="h-7 w-7 text-blue-600" />
                </div>

                <span className="mt-7 block text-xs font-bold tracking-[0.2em] text-blue-600">
                  {step.number}
                </span>

                <h3 className="mt-3 text-xl font-bold text-slate-900">
                  {step.title}
                </h3>

                <p className="mx-auto mt-3 max-w-xs leading-7 text-slate-500">
                  {step.description}
                </p>
              </article>
            );
          })}
        </div>

        <div className="mt-16 flex justify-center">
          <a
            href="#features"
            className="inline-flex items-center gap-2 text-sm font-semibold text-slate-700 transition-colors hover:text-blue-600"
          >
            See what you can do
            <ArrowRight className="h-4 w-4" />
          </a>
        </div>
      </div>
    </section>
  );
}

export default HowItWorksSection;