# GitHub Repository Setup Guide

This guide will help you create a new GitHub repository for the Finvaria project under your account.

## Quick Setup (Automated)

### Option 1: Using the PowerShell Script

We've created an automated script to make this easy:

```powershell
.\setup-new-repo.ps1
```

The script will:

1. Check if Git is installed
2. Ask for your GitHub username and desired repository name
3. Guide you through creating the repository on GitHub
4. Update the remote URL
5. Push all files to your new repository

### Option 2: Manual Setup

If you prefer to do it manually, follow these steps:

## Manual Setup Steps

### Step 1: Create Repository on GitHub

1. Go to [GitHub.com](https://github.com) and sign in
2. Click the **+** icon in the top right corner
3. Select **New repository**
4. Fill in the details:
    - **Repository name**: `Finvaria` (or any name you prefer)
    - **Description**: "Youth Empowerment Platform - AI-powered guidance for finance, legal, and
      education"
    - **Visibility**: Choose Public or Private
    - **Important**: Do NOT check "Initialize this repository with a README"
5. Click **Create repository**

### Step 2: Update Git Remote

Open PowerShell in the project directory and run:

```powershell
# Remove the old remote
git remote remove origin

# Add your new remote (replace YOUR_USERNAME and REPO_NAME)
git remote add origin https://github.com/YOUR_USERNAME/REPO_NAME.git

# Verify the remote was added
git remote -v
```

### Step 3: Push to GitHub

```powershell
# If you haven't committed yet
git add -A
git commit -m "Initial commit: Finvaria - Youth Empowerment Platform"

# Push to your new repository
git push -u origin main

# If 'main' doesn't work, try 'master'
# git push -u origin master
```

### Step 4: Verify Upload

1. Go to your repository on GitHub: `https://github.com/YOUR_USERNAME/REPO_NAME`
2. You should see all files uploaded
3. The README.md will be displayed on the repository homepage

## Troubleshooting

### Authentication Issues

If Git asks for credentials:

**Option 1: Use Personal Access Token (Recommended)**

1. Go to GitHub Settings → Developer settings → Personal access tokens → Tokens (classic)
2. Click "Generate new token (classic)"
3. Give it a name like "Finvaria"
4. Check the "repo" scope
5. Click "Generate token"
6. Copy the token (you won't see it again!)
7. When Git asks for password, paste the token instead

**Option 2: Use GitHub CLI**

```powershell
# Install GitHub CLI
winget install --id GitHub.cli

# Authenticate
gh auth login

# Then push again
git push -u origin main
```

### Branch Name Issues

If you get an error about branch names:

```powershell
# Check your current branch
git branch

# If it says 'master' instead of 'main', use:
git push -u origin master

# Or rename the branch to 'main'
git branch -M main
git push -u origin main
```

### Large File Issues

If you get errors about large files (like the AAR files):

```powershell
# The .gitignore should already handle this, but if needed:
# Make sure these paths are in .gitignore:
# app/libs/*.aar
# app/libs/temp/

# Remove large files from Git history if needed
git rm --cached app/libs/*.aar
git commit -m "Remove large AAR files"
git push -u origin main
```

## After Setup

Once your repository is on GitHub:

### Add Repository Details

1. Go to your repository on GitHub
2. Click the ⚙️ (settings) icon next to "About"
3. Add:
    - **Description**: "Youth Empowerment Platform - AI-powered guidance for finance, legal, and
      education"
    - **Website**: (if you have one)
    - **Topics**: `android`, `kotlin`, `jetpack-compose`, `ai`, `youth`, `education`, `finance`,
      `legal`, `hackathon`, `india`

### Update README (Optional)

You might want to update the README.md to:

- Add your name/team name
- Add screenshots
- Update the repository URL
- Add a demo video link

### Create Releases (Optional)

To create a release with your APK:

1. Go to your repository
2. Click "Releases" on the right sidebar
3. Click "Create a new release"
4. Tag it as `v1.0.0`
5. Upload the APK file from `app/build/outputs/apk/debug/app-debug.apk`
6. Add release notes
7. Click "Publish release"

## Keeping it Updated

To push future changes:

```powershell
# After making changes
git add -A
git commit -m "Description of changes"
git push
```

## Need Help?

If you run into any issues:

1. Check the [GitHub Git Guides](https://github.com/git-guides)
2. Run `git status` to see what's happening
3. Check `.git/config` to verify your remote URL

---

**Quick Command Reference:**

```powershell
# Check current remote
git remote -v

# Change remote URL
git remote set-url origin https://github.com/USERNAME/REPO.git

# Check Git status
git status

# See recent commits
git log --oneline -5

# Push to GitHub
git push -u origin main
```

Good luck with your repository! 🚀
