# Finvaria - Setup New GitHub Repository
# This script helps you push this project to a new GitHub repository

Write-Host "============================================" -ForegroundColor Cyan
Write-Host "Finvaria - GitHub Repository Setup" -ForegroundColor Cyan
Write-Host "============================================`n" -ForegroundColor Cyan

# Step 1: Check Git status
Write-Host "[1/5] Checking Git status..." -ForegroundColor Yellow
git --version
if ($LASTEXITCODE -ne 0) {
    Write-Host "Error: Git is not installed. Please install Git first." -ForegroundColor Red
    exit 1
}

# Step 2: Ask for repository details
Write-Host "`n[2/5] Repository Information" -ForegroundColor Yellow
Write-Host "Please provide the following information:`n"

$username = Read-Host "Enter your GitHub username"
$repoName = Read-Host "Enter repository name (e.g., Finvaria or startup_hackathon20)"

$fullRepoUrl = "https://github.com/$username/$repoName.git"

Write-Host "`nYour repository URL will be: $fullRepoUrl" -ForegroundColor Green

# Step 3: Confirm with user
Write-Host "`n[3/5] Before continuing, please:" -ForegroundColor Yellow
Write-Host "1. Go to GitHub.com and sign in"
Write-Host "2. Click the '+' icon in the top right"
Write-Host "3. Select 'New repository'"
Write-Host "4. Name it: $repoName"
Write-Host "5. Choose Public or Private"
Write-Host "6. DO NOT initialize with README, .gitignore, or license"
Write-Host "7. Click 'Create repository'`n"

$continue = Read-Host "Have you created the repository on GitHub? (y/n)"
if ($continue -ne "y" -and $continue -ne "Y") {
    Write-Host "Exiting. Please create the repository first." -ForegroundColor Red
    exit 1
}

# Step 4: Update remote
Write-Host "`n[4/5] Updating Git remote..." -ForegroundColor Yellow

# Remove old remote
Write-Host "Removing old remote origin..."
git remote remove origin 2>$null

# Add new remote
Write-Host "Adding new remote: $fullRepoUrl"
git remote add origin $fullRepoUrl

# Verify
Write-Host "`nCurrent remotes:"
git remote -v

# Step 5: Push to new repository
Write-Host "`n[5/5] Ready to push to GitHub!" -ForegroundColor Yellow
Write-Host "This will push all files to your new repository.`n"

$push = Read-Host "Push now? (y/n)"
if ($push -eq "y" -or $push -eq "Y") {
    Write-Host "`nPushing to GitHub..."
    
    # Check if we have commits
    $hasCommits = git log -1 2>$null
    if (!$hasCommits) {
        Write-Host "No commits found. Creating initial commit..."
        git add -A
        git commit -m "Initial commit: Finvaria - Youth Empowerment Platform"
    }
    
    # Push to GitHub
    git push -u origin main
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "`n============================================" -ForegroundColor Green
        Write-Host "SUCCESS! Repository setup complete!" -ForegroundColor Green
        Write-Host "============================================" -ForegroundColor Green
        Write-Host "`nYour repository is now available at:"
        Write-Host "https://github.com/$username/$repoName`n" -ForegroundColor Cyan
        Write-Host "Next steps:"
        Write-Host "1. Visit your repository on GitHub"
        Write-Host "2. Add a description and topics"
        Write-Host "3. Share it with others!`n"
    } else {
        Write-Host "`n============================================" -ForegroundColor Red
        Write-Host "Error: Push failed" -ForegroundColor Red
        Write-Host "============================================" -ForegroundColor Red
        Write-Host "`nPossible issues:"
        Write-Host "1. Repository might not exist on GitHub"
        Write-Host "2. You might need to authenticate (enter username/password or token)"
        Write-Host "3. Branch name might be different (try 'git push -u origin master')"
        Write-Host "`nYou can try pushing manually with:"
        Write-Host "git push -u origin main`n" -ForegroundColor Yellow
    }
} else {
    Write-Host "`nSetup complete! You can push later with:" -ForegroundColor Yellow
    Write-Host "git push -u origin main`n"
}

Write-Host "Script finished." -ForegroundColor Cyan
