# Contributing
We genuinely value your interest in making a contribution. Every contribution, regardless of its size, is highly appreciated and encouraged here.

## Project layout
- **Bukkit** - The primary module responsible for constructing the plugin for the Bukkit server platform and its derivatives (such as SpigotMC, PaperMC, and PurpurMC)
- **Modern** - Module that implements the interfaces from Shared and introduces libraries that may disable the plugin (Java bytecode higher than the server’s, or library not present on the server).
- **Shared** - A module that enables both Modern and Bukkit to connect without introducing a circular dependency, where module A relies on module B and module B depends on module A.

## How can I contribute to the code?
You can clone or fork the repository on GitHub and contribute in various ways, such as fixing bugs, updating dependency versions, optimizing code, or any other assistance you can provide.

### Cloning the repository:
![Fork the Repository in GitHub](art/fork-button.png)

**or**

```sh
git clone https://github.com/meme20200/dupeplus.git
cd dupeplus
```
### Ready to share your work?
Before you share your work, please ensure you’ve reviewed and ticked all the items on the checklist.
#### Checklist
- [ ] Does it build (shadowJar) successfully?
- [ ] Does it run with no issues on modern versions (e.g., 1.21.11, 26.2, 26.3)

Now, it’s time to help the DupePlus repository with your remarkable work by submitting a pull request to the main branch of the DupePlus repository. To do this, navigate to the Pull Requests section within the repository, and click on "New Pull Request".
https://github.com/meme20200/dupeplus/pulls

