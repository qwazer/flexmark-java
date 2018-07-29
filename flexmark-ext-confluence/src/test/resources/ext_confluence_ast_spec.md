---
title: Confluence Extension Spec
author: Anton Reshetnikov
version:
date: '2018-07-28'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---


## Note without title

```````````````````````````````` example Note without title: 1
> **info:**
> 
> Contents of my note
>
.
{info}
Contents of my note

{info}

.
Document[0, 39]
  ConfluenceBlock[4, 37] type:[4, 8, "info"]
    BlockQuote[12, 37] marker:[12, 13, ">"]
      Paragraph[17, 37] isTrailingBlankLine
        Text[17, 36] chars:[17, 36, "Conte …  note"]
````````````````````````````````

## Note with title

```````````````````````````````` example Note with title: 1
> **info:** Aboutme
> 
> Lorem ipsum dolor sit amet, 
> consectetur adipiscing elit
>
.
{info}
Lorem ipsum dolor sit amet, consectetur adipiscing elit

{info}

.
Document[0, 86]
  ConfluenceBlock[4, 84] type:[4, 8, "info"] title:[12, 19, "Aboutme"]
    BlockQuote[20, 84] marker:[20, 21, ">"]
      Paragraph[25, 84] isTrailingBlankLine
        Text[25, 52] chars:[25, 52, "Lorem … amet,"]
        SoftLineBreak[53, 54]
        Text[56, 83] chars:[56, 83, "conse …  elit"]
````````````````````````````````
