<div class="clearfix #{ifError _name}error#{/ifError}">
   <label for="${_id?:_name}">&{_code}</label>
   <div class="input">
     <input type="password" name="${_name}" id="${_id?:_name}" class="${_cssClass}">
     #{ifError _name}<span class="help-inline">#{error _name /}</span>#{/ifError}
   </div>
</div>		