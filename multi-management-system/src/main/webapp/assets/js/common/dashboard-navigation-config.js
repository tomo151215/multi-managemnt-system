"use strict";
// head.jspfでhtmx.min.js、既存htmx-config.jsの後に1回だけdeferで読み込みます。
htmx.config.historyRestoreAsHxRequest = false;
htmx.config.allowScriptTags = false;
htmx.config.allowEval = false;
htmx.config.includeIndicatorStyles = false;
htmx.config.timeout = 15000;
