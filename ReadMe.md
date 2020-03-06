# Secure Properties GUI
## A TornadoFx application for encrypt/decrypt of Mule 4 properties
### Mule 4 provides a jar file and instructions for encrypting and decrypting properties. Some environments would benefit from a GUI for DevOps teams or other staff to perform these functions.

In my blog post [Securing Mule 4 config properties](https://bestow.info/securing-mule-4-config-properties) I discuss how you would go about securing your YAML and .property files. One of the requirements is to downloading a Mule Jar file and manually encoding or decoding secrets. Manual processes can be error prone or inappropriate for support teams to use.

I'm still considering an appropriate layout for runtime Jar files and will update this section to describe how you might add external Jar files such as JCE to enhance the cryptographic algorithms and ciphers which can be used.

**TornadoFx GUI application**
![alt text][snap]

[snap]: https://user-images.githubusercontent.com/4605105/76032967-0adfb980-5f09-11ea-86b8-d5f79a82f3c3.PNG "Secure Porps screen shot"

**Gist for image uploads**
[here](https://gist.github.com/vinkla/dca76249ba6b73c5dd66a4e986df4c8d)