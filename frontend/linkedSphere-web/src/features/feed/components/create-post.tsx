import { Image, Send } from "lucide-react";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

function CreatePost() {
  return (
    <section className="rounded-xl border bg-white p-4 shadow-sm">
      <div className="flex gap-3">
        <div className="flex h-10 w-10 shrink-0 items-center justify-center rounded-full bg-blue-600 font-semibold text-white">
          LS
        </div>

        <Input
          placeholder="What do you want to share?"
          className="rounded-full"
        />
      </div>

      <div className="mt-4 flex items-center justify-between border-t pt-3">
        <Button
          variant="ghost"
          className="gap-2"
        >
          <Image className="h-4 w-4" />
          Photo
        </Button>

        <Button className="gap-2">
          <Send className="h-4 w-4" />
          Post
        </Button>
      </div>
    </section>
  );
}

export default CreatePost;