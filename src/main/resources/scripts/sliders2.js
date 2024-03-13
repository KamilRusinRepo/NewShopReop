const slider = document.querySelector(".Apple_cards_frame")
const arrow = document.querySelectorAll(".Apple_button");
const firstCardWidth = slider.querySelector(".Apple_card").offsetWidth;
const paragraph = document.getElementById("ok");
const text = document.getElementById("ok").innerText;
const textLength = text.trim().length;

if(textLength >= 20){
    paragraph.style.display = "flex";
}else{
    paragraph.style.display = "block";
}

const sliders = document.querySelector(".Samsung_cards_frame")
const arrows = document.querySelectorAll(".Samsung_button");
const firstCardWidths = sliders.querySelector(".Samsung_card").offsetWidth;


arrows.forEach(btn => {
    btn.addEventListener("click", () => {
        sliders.scrollLeft += btn.id === "S_left_button" ? -firstCardWidth : firstCardWidth;
    });
});

arrow.forEach(btn => {
    btn.addEventListener("click", () => {
        slider.scrollLeft += btn.id === "A_left_button" ? -firstCardWidth : firstCardWidth;
    });
});