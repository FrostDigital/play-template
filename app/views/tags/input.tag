<div class="clearfix #{ifError _name}error#{/ifError}">
   <label for="${_id?:_name}">&{_code}</label>
   <div class="input">
     <input type="input" ${_maxLength ? 'maxlength="' +  _maxLength + '"' : ''} name="${_name}" id="${_id?:_name}" class="${_cssClass}" value="${_value}"  ${_disabled ? 'disabled="disabled"' : ''} />
     #{ifError _name}<span class="help-inline">#{error _name /}</span>#{/ifError}
   </div>
</div>		