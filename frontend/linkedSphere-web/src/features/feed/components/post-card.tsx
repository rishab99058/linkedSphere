import { MoreHorizontal, ThumbsUp, MessageCircle, Share2 } from "lucide-react";

import { Button } from "@/components/ui/button";

interface PostCardProps {
  author: string;
  time: string;
  content: string;
}

function PostCard({
  author,
  time,
  content,
}: PostCardProps) {
  return (
    <article className="rounded-xl border bg-white p-4 shadow-sm">
      {/* Author */}
      <div className="flex items-center justify-between">
        <div className="flex items-center gap-3">
          <div className="flex h-10 w-10 items-center justify-center rounded-full bg-slate-200 font-semibold">
            {author.charAt(0).toUpperCase()}
          </div>

          <div>
            <p className="font-semibold">
              {author}
            </p>

            <p className="text-xs text-slate-500">
              {time}
            </p>
          </div>
        </div>

        <Button
          variant="ghost"
          size="icon"
        >
          <MoreHorizontal />
        </Button>
      </div>

      {/* Content */}
      <p className="mt-4 text-sm leading-6 text-slate-700">
        {content}
      </p>

      {/* Actions */}
      <div className="mt-4 flex border-t pt-2">
        <Button
          variant="ghost"
          className="flex-1 gap-2"
        >
          <ThumbsUp className="h-4 w-4" />
          Like
        </Button>

        <Button
          variant="ghost"
          className="flex-1 gap-2"
        >
          <MessageCircle className="h-4 w-4" />
          Comment
        </Button>

        <Button
          variant="ghost"
          className="flex-1 gap-2"
        >
          <Share2 className="h-4 w-4" />
          Share
        </Button>
      </div>
    </article>
  );
}

export default PostCard;