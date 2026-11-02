document.addEventListener('DOMContentLoaded', () => {
    // State management indicators
    let currentBalance = 0;

    // Dom element cache references
    const elements = {
        balanceDisplay: document.querySelector('header span.font-extrabold'),
        watchVideoBtn: document.querySelector('main section button'),
        entertainmentBtn: document.querySelector('button:has(.fa-clapperboard)'),
        billsBtn: document.querySelector('button:has(.fa-wallet)'),
        newsBtn: document.querySelector('button:has(.fa-newspaper)'),
        streakBtn: document.querySelector('button:has(.fa-fire)')
    };

    // Helper function to update system balance UI
    function updateBalanceUI(amount) {
        currentBalance += amount;
        if (elements.balanceDisplay) {
            elements.balanceDisplay.textContent = `${currentBalance} paws`;
        }
        console.log(`Balance updated: +${amount} paws. New balance: ${currentBalance}`);
    }

    // Rewarded video handler engine simulation
    if (elements.watchVideoBtn) {
        elements.watchVideoBtn.addEventListener('click', () => {
            console.log("Loading AdMob rewarded video placement request...");
            elements.watchVideoBtn.disabled = true;
            elements.watchVideoBtn.innerText = "Loading Ad...";

            // Simulate a standard 3-second rewarded video runtime experience
            setTimeout(() => {
                alert("Video completed! You earned 50 paws.");
                updateBalanceUI(50);
                
                // Reset button capability state
                elements.watchVideoBtn.disabled = false;
                elements.watchVideoBtn.innerHTML = `<i class="fa-solid fa-circle-play text-base"></i><span>Watch Video</span>`;
            }, 3000);
        });
    }

    // Category navigation routing event attachments
    if (elements.entertainmentBtn) {
        elements.entertainmentBtn.addEventListener('click', () => {
            console.log("Navigating entry route to Entertainment Module...");
            // TODO: Link window.location.href target or Supabase log entries
        });
    }

    if (elements.billsBtn) {
        elements.billsBtn.addEventListener('click', () => {
            console.log("Navigating entry route to Bills Payment Manager...");
        });
    }

    if (elements.newsBtn) {
        elements.newsBtn.addEventListener('click', () => {
            console.log("Navigating entry route to News Feed Registry...");
        });
    }

    if (elements.streakBtn) {
        elements.streakBtn.addEventListener('click', () => {
            console.log("Processing Daily Check-in Streak action...");
            updateBalanceUI(10); // Reward standard baseline interaction coins
            alert("Daily check-in successful! Earned 10 paws.");
        });
    }
});
