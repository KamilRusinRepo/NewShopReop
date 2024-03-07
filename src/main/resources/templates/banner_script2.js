 let list = document.querySelector('.banner .list');
 let items = document.querySelectorAll('.banner .list .item');
 let dots = document.querySelectorAll('.dots li');
 let prev = document.getElementById('prev');
 let next = document.getElementById('next');
 let photo = document.querySelectorAll('.banner .list .item img');
 let buttons = document.querySelector('.buttons');
 let baner = document.querySelector('.banner');

 let active = 0;
 let itemsLength = items.length - 1;

 prev.onclick = function(){
   if(active - 1 < 0){
       active = itemsLength;
   }
   else{
       active -=1;
   }
   reloadSlider();
}
next.onclick = function(){
   if(active + 1 > itemsLength){
       active = 0;
   }
   else{
       active += 1;
   }
   reloadSlider();
}

 let refreshSlider = setInterval(() => {next.click()}, 3000);
 function reloadSlider(){
    let checkLeft = items[active].offsetLeft;
    list.style.left = -checkLeft  + "px";

    let lastActive = document.querySelector('.dots li.active');
    lastActive.classList.replace('active', 'noactive');
    dots[active].classList.replace('noactive', 'active');
    clearInterval(refreshSlider);
    refreshSlider = setInterval(() => {next.click()}, 3000);

 }

 baner.addEventListener('mouseover', () => {
    photo[active].style.width = 1020 + "px";
 });

 baner.addEventListener('mouseout', () => {
    photo[active].style.width = 1000 + "px";
 });

 buttons.addEventListener('mouseover', () => {
    photo[active].style.width = 1020 + "px";
 });

dots.forEach((li, key) => {
    li.addEventListener('click', function(){
        active = key;
        reloadSlider();
    })
});

