import {
  Card,
  CardContent,
} from "@/components/ui/card";

interface PostCardProps {
  name: string;
  headline: string;
  content: string;
}

function PostCard({
  name,
  headline,
  content,
}: PostCardProps) {
  return (
    <Card>
      <CardContent className="p-5">
        <div className="flex gap-3">
          <div className="flex h-11 w-11 shrink-0 items-center justify-center rounded-full bg-blue-100 font-semibold text-blue-700">
            {name.charAt(0)}
          </div>

          <div>
            <p className="font-semibold text-slate-950">
              {name}
            </p>

            <p className="text-xs text-slate-500">
              {headline}
            </p>
          </div>
        </div>

        <p className="mt-5 leading-7 text-slate-700">
          {content}
        </p>
      </CardContent>
    </Card>
  );
}

export default PostCard;