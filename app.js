document.addEventListener('DOMContentLoaded', () => {
    // 1. Core Supabase Project Configuration Initializers
    // TODO: Replace placeholders with your actual Supabase Dashboard Project credentials
    const SUPABASE_URL = 'https://YOUR_SUPABASE_PROJECT_URL.supabase.co';
    const SUPABASE_ANON_KEY = 'YOUR_SUPABASE_ANON_PUBLIC_KEY';
    
    const supabase = window.supabase ? window.supabase.createClient(SUPABASE_URL, SUPABASE_ANON_KEY) : null;

    let currentBalance = 0;
    let userId = null;

    // Dom element cache references
    const elements = {
        balanceDisplay: document.querySelector('header span.font-extrabold'),
        watchVideoBtn: document.querySelector('main section button'),
        entertainmentBtn: document.querySelector('button:has(.fa-clapperboard)'),
        billsBtn: document.querySelector('button:has(.fa-wallet)'),
        newsBtn: document.querySelector('button:has(.fa-newspaper)'),
        streakBtn: document.querySelector('button:has(.fa-fire)')
    };

    // 2. Fetch and synchronize user's current cloud database balance ledger state
    async function loadUserData() {
        if (!supabase) return;
        
        // Retrieve current active user session context matching
        const { data: { user } } = await supabase.auth.getUser();
        if (!user) {
            console.log("No authenticated user session detected. Operating in local sandbox view mode.");
            return;
        }

        userId = user.id;

        // Fetch balance details from profile record row data source
        const { data, error } = await supabase
            .from('profiles')
            .select('paw_balance')
            .eq('id', userId)
            .single();

        if (error) {
            console.error("Database connection fault retrieving profile metrics:", error.message);
        } else if (data) {
            currentBalance = data.paw_balance;
            renderBalanceUI();
        }
    }

    // 3. Mutate balance transactions securely on cloud backend profiles matrix
    async function addPawsToAccount(amount, transactionContextLabel) {
        currentBalance += amount;
        renderBalanceUI();

        if (supabase && userId) {
            const { error } = await supabase
                .from('profiles')
                .update({ paw_balance: currentBalance, updated_at: new Date().toISOString() })
                .eq('id', userId);

            if (error) {
                console.error(`Transaction ledger update failed for [${transactionContextLabel}]:`, error.message);
            } else {
                console.log(`Cloud sync success for [${transactionContextLabel}]: +${amount} paws saved.`);
            }
        }
    }

    function renderBalanceUI() {
        if (elements.balanceDisplay) {
            elements.balanceDisplay.textContent = `${currentBalance} paws`;
        }
    }

    // 4. Rewarded video handler engine simulation
    if (elements.watchVideoBtn) {
        elements.watchVideoBtn.addEventListener('click', () => {
            console.log("Loading AdMob rewarded video placement request...");
            elements.watchVideoBtn.disabled = true;
            elements.watchVideoBtn.innerText = "Loading Ad...";

            setTimeout(async () => {
                alert("Video completed! You earned 50 paws.");
                await addPawsToAccount(50, "AdMob Rewarded Video Action");
                
                elements.watchVideoBtn.disabled = false;
                elements.watchVideoBtn.innerHTML = `<i class="fa-solid fa-circle-play text-base"></i><span>Watch Video</span>`;
            }, 3000);
        });
    }

    // 5. Daily Check-in Streak action routing tracking
    if (elements.streakBtn) {
        elements.streakBtn.addEventListener('click', async () => {
            console.log("Processing Daily Check-in Streak action...");
            await addPawsToAccount(10, "Daily Check-in Reward Integration");
            alert("Daily check-in successful! Earned 10 paws.");
        });
    }

    // Initialize application sequence on script initialization
    loadUserData();
});

// --- MINIGAMES BLOCK ENGINE EXTENSION ---
document.addEventListener('DOMContentLoaded', () => {
    // Local session constraints tracking state metrics
    let spinsRemaining = 5;
    let pendingChestPaws = 0.0;
    const maxChestCapacity = 10.0; // Secure distribution threshold safeguard
    
    const ui = {
        chestPending: document.getElementById('chest-pending-display'),
        chestProgress: document.getElementById('chest-progress'),
        claimChestBtn: document.getElementById('claim-chest-btn'),
        spinLimitBadge: document.getElementById('spin-limit-badge'),
        wheelStatus: document.getElementById('wheel-status-text'),
        triggerSpinBtn: document.getElementById('trigger-spin-btn')
    };

    // A. Automated Background Chest Tick Generator Loop Tracker
    setInterval(() => {
        if (pendingChestPaws < maxChestCapacity) {
            pendingChestPaws += 0.1;
            if (ui.chestPending) ui.chestPending.textContent = `${pendingChestPaws.toFixed(2)} paws`;
            
            // Calculate scale percentage for display filling mechanics
            const fillPercentage = (pendingChestPaws / maxChestCapacity) * 100;
            if (ui.chestProgress) ui.chestProgress.style.width = `${fillPercentage}%`;
            
            // Activate button interaction target if minimum criteria met
            if (pendingChestPaws >= 1.0 && ui.claimChestBtn && ui.claimChestBtn.disabled) {
                ui.claimChestBtn.disabled = false;
                ui.claimChestBtn.className = "w-full bg-amber-500 hover:bg-amber-600 text-white font-bold text-xs py-2.5 rounded-xl transition-all duration-150 shadow-md shadow-amber-500/10 flex items-center justify-center space-x-1.5 cursor-pointer";
            }
        }
    }, 1000);

    // B. Claim Chest Action Callback Execution Handler Route
    if (ui.claimChestBtn) {
        ui.claimChestBtn.addEventListener('click', () => {
            const claimAmount = Math.floor(pendingChestPaws);
            if (claimAmount >= 1) {
                pendingChestPaws = 0.0;
                if (ui.chestProgress) ui.chestProgress.style.width = "0%";
                
                // Invoke core account transaction balance increments tracking module
                if (typeof window.addPawsToAccount === "function") {
                    window.addPawsToAccount(claimAmount, "Passive Chest Distribution Claim");
                } else {
                    // Local sandbox baseline configuration fallback state handler fallback logic
                    const balanceEl = document.querySelector('header span.font-extrabold');
                    if (balanceEl) {
                        let current = parseInt(balanceEl.textContent) || 0;
                        balanceEl.textContent = `${current + claimAmount} paws`;
                    }
                }

                alert(`Successfully harvested ${claimAmount} paws from the Mining Chest!`);
                
                // Return interface components to standard locked state definitions
                ui.claimChestBtn.disabled = true;
                ui.claimChestBtn.className = "w-full bg-slate-100 text-slate-400 font-bold text-xs py-2.5 rounded-xl transition-all duration-150 flex items-center justify-center space-x-1.5 cursor-not-allowed";
            }
        });
    }

    // C. Capped Spin Lottery Mechanical Execution Trigger Block
    if (ui.triggerSpinBtn) {
        ui.triggerSpinBtn.addEventListener('click', () => {
            if (spinsRemaining <= 0) {
                alert("Daily distribution cap reached! Come back tomorrow to secure your high margins.");
                return;
            }

            spinsRemaining--;
            if (ui.spinLimitBadge) ui.spinLimitBadge.textContent = `${spinsRemaining} left today`;
            
            // Lock control rows to avoid high speed overlap event calls
            ui.triggerSpinBtn.disabled = true;
            if (ui.wheelStatus) {
                ui.wheelStatus.className = "text-xs font-extrabold text-rose-500 tracking-widest animate-bounce uppercase";
                ui.wheelStatus.textContent = "Spinning Wheel...";
            }

            // Simulate distribution wheel randomization logic loop timings
            setTimeout(() => {
                const prizePoolTiers =; // Secure economic margin metrics arrays
                const chosenPrize = prizePoolTiers[Math.floor(Math.random() * prizePoolTiers.length)];
                
                if (ui.wheelStatus) {
                    ui.wheelStatus.className = "text-xs font-extrabold text-emerald-600 tracking-wide uppercase";
                    ui.wheelStatus.textContent = `Won +${chosenPrize} Paws!`;
                }

                if (typeof window.addPawsToAccount === "function") {
                    window.addPawsToAccount(chosenPrize, "Lucky Lottery Spin Reward Engine");
                } else {
                    const balanceEl = document.querySelector('header span.font-extrabold');
                    if (balanceEl) {
                        let current = parseInt(balanceEl.textContent) || 0;
                        balanceEl.textContent = `${current + chosenPrize} paws`;
                    }
                }

                // Unlock trigger elements context states matrices values rows entries
                ui.triggerSpinBtn.disabled = false;
            }, 2000);
        });
    }
});

// === MINING CHEST & LUCKY SPIN WHEEL MODULE OPERATIONS ===
document.addEventListener('DOMContentLoaded', () => {
    let unclaimableMinedPaws = 0.000;
    let availableSpinsLeft = 5;

    const gameElements = {
        minedCounterDisplay: document.getElementById('mined-counter'),
        claimMiningBtn: document.getElementById('claim-mining-btn'),
        spinCountDisplay: document.getElementById('spin-count-display'),
        wheelStatusText: document.getElementById('wheel-status-text'),
        spinWheelBtn: document.getElementById('spin-wheel-btn')
    };

    // 1. Passive Mining Loop (Increments 0.005 paws every 1.5 seconds)
    if (gameElements.minedCounterDisplay) {
        setInterval(() => {
            // Cap background accumulation up to a reasonable buffer threshold
            if (unclaimableMinedPaws < 50.00) {
                unclaimableMinedPaws += 0.005;
                gameElements.minedCounterDisplay.textContent = unclaimableMinedPaws.toFixed(3);
                
                // Toggle active state conditionally based on min amount
                if (unclaimableMinedPaws >= 1.00 && gameElements.claimMiningBtn.disabled) {
                    gameElements.claimMiningBtn.disabled = false;
                    gameElements.claimMiningBtn.className = "w-full bg-amber-500 hover:bg-amber-600 text-white font-bold text-xs py-3 rounded-xl transition-all duration-150 active:scale-98 shadow-md shadow-amber-500/10";
                }
            }
        }, 1500);
    }

    // 2. Claim Accumulation Event Handler
    if (gameElements.claimMiningBtn) {
        gameElements.claimMiningBtn.addEventListener('click', () => {
            const integerRewardAmt = Math.floor(unclaimableMinedPaws);
            if (integerRewardAmt >= 1) {
                // Call global application add transaction hook safely
                if (typeof window.addPawsToAccount === 'function') {
                    window.addPawsToAccount(integerRewardAmt, "Passive Mining Chest Yield Claim");
                } else {
                    alert(`Claimed ${integerRewardAmt} paws successfully!`);
                }
                
                unclaimableMinedPaws -= integerRewardAmt;
                gameElements.minedCounterDisplay.textContent = unclaimableMinedPaws.toFixed(3);
                
                // Re-evaluate limits
                if (unclaimableMinedPaws < 1.00) {
                    gameElements.claimMiningBtn.disabled = true;
                    gameElements.claimMiningBtn.className = "w-full bg-slate-100 text-slate-400 font-bold text-xs py-3 rounded-xl transition-all duration-150 active:scale-98";
                }
            }
        });
    }

    // 3. Lucky Wheel Spin Matrix Simulation
    if (gameElements.spinWheelBtn) {
        gameElements.spinWheelBtn.addEventListener('click', () => {
            if (availableSpinsLeft <= 0) {
                alert("Daily spin quota reached! Resets in 24 hours.");
                return;
            }

            availableSpinsLeft--;
            gameElements.spinCountDisplay.textContent = `${availableSpinsLeft} / 5 Spins Left`;
            gameElements.spinWheelBtn.disabled = true;
            gameElements.wheelStatusText.textContent = "Spinning Wheel... 🎰";
            gameElements.wheelStatusText.className = "font-extrabold text-sm tracking-wide uppercase text-center animate-pulse text-amber-100";

            // Process spin animation duration window
            setTimeout(() => {
                const prizePoolOptions =;
                const randomizedReward = prizePoolOptions[Math.floor(Math.random() * prizePoolOptions.length)];

                gameElements.wheelStatusText.textContent = `🎉 You Won +${randomizedReward} Paws!`;
                gameElements.wheelStatusText.className = "font-extrabold text-base tracking-wide uppercase text-center text-white drop-shadow-md";

                if (typeof window.addPawsToAccount === 'function') {
                    window.addPawsToAccount(randomizedReward, "Lucky Wheel Allocation Draw");
                }

                // Restore active triggers if threshold is valid
                if (availableSpinsLeft > 0) {
                    gameElements.spinWheelBtn.disabled = false;
                } else {
                    gameElements.spinWheelBtn.className = "w-full bg-slate-100 text-slate-400 font-bold text-xs py-3 rounded-xl transition-all duration-150 active:scale-98";
                    gameElements.spinWheelBtn.textContent = "No Spins Remaining Today";
                }
            }, 2500);
        });
    }
});

// === ONE WALLET REDEMPTION SYSTEM OPERATIONS ===
document.addEventListener('DOMContentLoaded', () => {
    let selectedMethod = 'GCash';
    let selectedAmountPHP = null;
    const CONVERSION_RATE_PAWS_PER_PHP = 17.24; // Corresponds directly to 1,000 paws = $1.00 USD / ₱58.00 PHP

    const walletElements = {
        methodButtons: document.querySelectorAll('.payment-method-btn'),
        tierButtons: document.querySelectorAll('.cashout-tier-btn'),
        withdrawBtn: document.getElementById('withdraw-action-btn')
    };

    // 1. Manage payment channel selection visual toggles
    walletElements.methodButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            walletElements.methodButtons.forEach(b => {
                b.className = "payment-method-btn border border-slate-100 hover:border-slate-200 bg-slate-50/50 p-3.5 rounded-2xl flex flex-col items-center justify-center text-center transition-all duration-150 active:scale-98";
            });
            btn.className = "payment-method-btn border-2 border-blue-500 bg-blue-50/50 p-3.5 rounded-2xl flex flex-col items-center justify-center text-center transition-all duration-150 active:scale-98";
            selectedMethod = btn.getAttribute('data-method');
            console.log(`Payment routing channel changed to: ${selectedMethod}`);
        });
    });

    // 2. Manage payment value matrix level toggles
    walletElements.tierButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            walletElements.tierButtons.forEach(b => {
                b.className = "cashout-tier-btn border border-slate-200 hover:border-blue-500 hover:bg-blue-50/20 p-3 rounded-xl text-xs font-extrabold text-slate-700 transition-all duration-150 active:scale-95";
            });
            btn.className = "cashout-tier-btn border-2 border-blue-500 bg-blue-50 text-xs font-extrabold text-blue-700 p-3 rounded-xl transition-all duration-150 active:scale-95";
            selectedAmountPHP = parseInt(btn.getAttribute('data-amount'), 10);
            console.log(`Selected cashout settlement goal amount: ₱${selectedAmountPHP} PHP`);
        });
    });

    // 3. Process verification checking on balance availability before settlement requests
    if (walletElements.withdrawBtn) {
        walletElements.withdrawBtn.addEventListener('click', () => {
            if (!selectedAmountPHP) {
                alert("Please select a withdrawal amount tier first.");
                return;
            }

            const requiredPaws = Math.ceil(selectedAmountPHP * CONVERSION_RATE_PAWS_PER_PHP);
            
            // Access current runtime variable scope via display element parsing
            const currentUIBalanceText = document.querySelector('header span.font-extrabold')?.textContent || "0";
            const evaluatedPawsBalance = parseInt(currentUIBalanceText, 10) || 0;

            if (evaluatedPawsBalance < requiredPaws) {
                alert(`Insufficient balance! Withdrawal of ₱${selectedAmountPHP} requires at least ${requiredPaws} paws. You need ${requiredPaws - evaluatedPawsBalance} more paws.`);
            } else {
                alert(`Withdrawal request submitted successfully!\nChannel: ${selectedMethod}\nAmount: ₱${selectedAmountPHP} PHP\nCost: ${requiredPaws} paws deducted.\n\nOur ledger administrators will verify your payout within 24 hours.`);
                // Hook to execute transaction mutations down onto Supabase profiles array
                if (typeof window.addPawsToAccount === 'function') {
                    window.addPawsToAccount(-requiredPaws, `Cashout Payout Request: ₱${selectedAmountPHP} via ${selectedMethod}`);
                }
            }
        });
    }
});
