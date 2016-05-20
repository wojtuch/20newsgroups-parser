# 20newsgroup-parser

Simple tool for parsing the contents of the [20_newsgroups.tar.gz](https://archive.ics.uci.edu/ml/datasets/Twenty+Newsgroups) archive.
I've built it since I couldn't find such software for Java.

Consider an example document:

    Newsgroups: sci.med
    Path: cantaloupe.srv.cs.cmu.edu!das-news.harvard.edu!spdcc!dyer
    From: dyer@spdcc.com (Steve Dyer)
    Subject: Re: food-related seizures?
    Message-ID: <1993Apr19.204410.6273@spdcc.com>
    Organization: S.P. Dyer Computer Consulting, Cambridge MA
    References: <79727@cup.portal.com> <1993Apr17.184305.18758@spdcc.com> <PAULSON.93Apr19081647@cmb00.larc.nasa.gov>
    Date: Mon, 19 Apr 1993 20:44:10 GMT

    My comments about the Feingold Diet have no relevance to your
    daughter's purported FrostedFlakes-related seizures.  I can't imagine
    why you included it.

    --
    Steve Dyer
    dyer@ursa-major.spdcc.com aka {ima,harvard,rayssd,linus,m2c}!spdcc!dyer

To parse it, just instantiate a `NewsgroupParser` passing the path to the uncompressed tarball, and call the `parse()` method on the parser.
After the files have been parsed, you can access them via `getArticles()`.
This gives as key-value collection, with newsgroups labels as keys, and the lists of parsed articles as values.
Every article consists of the text and the key-value collection of headers.