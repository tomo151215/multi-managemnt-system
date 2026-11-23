import { copyFile, mkdir } from "node:fs/promises";

var destination = new URL(
    "../src/main/webapp/assets/vendor/",
    import.meta.url
);

// コピー先のフォルダーを作る
await mkdir(destination, { recursive: true });

// HTMX本体をコピーする
await copyFile(
    new URL("../node_modules/htmx.org/dist/htmx.min.js", import.meta.url),
    new URL("htmx.min.js", destination)
);

// ライセンスもコピーする
await copyFile(
    new URL("../node_modules/htmx.org/LICENSE", import.meta.url),
    new URL("htmx.LICENSE", destination)
);