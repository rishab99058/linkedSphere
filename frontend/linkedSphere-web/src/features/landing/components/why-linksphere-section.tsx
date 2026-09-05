import {
  HeartHandshake,
  Lightbulb,
  Users,
} from "lucide-react";

const reasons = [
  {
    icon: HeartHandshake,
    title: "Meaningful connections",
    description:
      "Focus on relationships that actually add value to your professional journey.",
  },
  {
    icon: Lightbulb,
    title: "Professional discovery",
    description:
      "Discover new ideas, perspectives, communities, and possibilities.",
  },
  {
    icon: Users,
    title: "Community-driven growth",
    description:
      "Learn from people around you and contribute something valuable in return.",
  },
];

function WhyLinkedSphereSection() {
  return (
    <section
      id="why"
      className="bg-white px-5 py-24 sm:px-8 lg:px-10 lg:py-32"
    >
      <div className="mx-auto max-w-7xl">
        <div className="mx-auto max-w-3xl text-center">
          <p className="text-sm font-semibold uppercase tracking-[0.2em] text-blue-600">
            Why LinkedSphere
          </p>

          <h2 className="mt-4 text-4xl font-bold tracking-tight text-slate-900 sm:text-5xl">
            Professional networking
            <br />
            <span className="text-blue-600">
              without the noise.
            </span>
          </h2>

          <p className="mt-6 text-lg leading-8 text-slate-500">
            Your professional network should feel human. LinkedSphere is built
            around relationships, conversations, and shared growth.
          </p>
        </div>

        <div className="mt-16 grid gap-5 md:grid-cols-3">
          {reasons.map((reason) => {
            const Icon = reason.icon;

            return (
              <article
                key={reason.title}
                className="rounded-3xl border border-slate-200 bg-slate-50 p-8 text-center"
              >
                <div className="mx-auto flex h-14 w-14 items-center justify-center rounded-2xl bg-blue-100 text-blue-600">
                  <Icon className="h-6 w-6" />
                </div>

                <h3 className="mt-6 text-xl font-bold text-slate-900">
                  {reason.title}
                </h3>

                <p className="mt-3 leading-7 text-slate-500">
                  {reason.description}
                </p>
              </article>
            );
          })}
        </div>

        {/* Development placeholder metrics */}
        <div className="mt-16 overflow-hidden rounded-3xl border border-slate-200">
          <div className="grid sm:grid-cols-3">
            <div className="border-b border-slate-200 p-8 text-center sm:border-b-0 sm:border-r">
              <p className="text-4xl font-bold text-slate-900">
                10K+
              </p>

              <p className="mt-2 text-sm text-slate-500">
                Meaningful connections
              </p>
            </div>

            <div className="border-b border-slate-200 p-8 text-center sm:border-b-0 sm:border-r">
              <p className="text-4xl font-bold text-slate-900">
                5K+
              </p>

              <p className="mt-2 text-sm text-slate-500">
                Professionals discovering
              </p>
            </div>

            <div className="p-8 text-center">
              <p className="text-4xl font-bold text-slate-900">
                100+
              </p>

              <p className="mt-2 text-sm text-slate-500">
                Opportunities created
              </p>
            </div>
          </div>
        </div>

        <p className="mt-3 text-center text-xs text-slate-400">
          Example platform metrics — development placeholders.
        </p>
      </div>
    </section>
  );
}

export default WhyLinkedSphereSection;